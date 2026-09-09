package com.estacionamiento.inteligente.data.remote

import android.util.Log
import com.estacionamiento.inteligente.data.model.ChatMessage
import com.estacionamiento.inteligente.data.model.Coordinates
import com.estacionamiento.inteligente.data.model.ParkingSpot
import com.estacionamiento.inteligente.data.remote.model.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class GeminiRepository(private val apiKey: String) {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()
    
    private val gson = Gson()
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun queryGemini(
        userQuery: String,
        parkings: List<ParkingSpot>,
        userLocation: Coordinates,
        history: List<ChatMessage> = emptyList()
    ): ChatMessage = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) {
            return@withContext performOfflineFallback(userQuery, parkings)
        }

        val prompt = buildPrompt(userQuery, parkings, userLocation, history)
        val requestBody = GeminiRequest(
            contents = listOf(Content(parts = listOf(Part(text = prompt)))),
            generationConfig = GenerationConfig(temperature = 0.4, maxOutputTokens = 1000)
        )
        val jsonRequest = gson.toJson(requestBody)
        
        // Cadena de modelos optimizada por VELOCIDAD (Flash latest primero)
        val modelChain = listOf("gemini-flash-latest", "gemini-1.5-flash", "gemini-1.5-flash-8b", "gemini-3.8-flash")
        
        for (modelName in modelChain) {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/$modelName:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .post(jsonRequest.toRequestBody(mediaType))
                .build()

            var retryCount = 0
            val maxRetries = 2
            
            while (retryCount <= maxRetries) {
                try {
                    val response = client.newCall(request).execute()
                    if (response.isSuccessful) {
                        val body = response.body?.string()
                        val geminiResponse = gson.fromJson(body, GeminiResponse::class.java)
                        val replyText = geminiResponse.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                            ?: "No pude procesar tu solicitud."
                        
                        return@withContext parseGeminiReply(replyText)
                    } else if (response.code == 503 || response.code == 429) {
                        if (retryCount < maxRetries) {
                            retryCount++
                            kotlinx.coroutines.delay(1500L * retryCount)
                            continue
                        } else {
                            // Si agotamos reintentos en este modelo, pasamos al siguiente de la cadena
                            break 
                        }
                    } else {
                        Log.e("GeminiRepo", "Error en modelo $modelName: ${response.code} - ${response.message}")
                        break // Error no recuperable para este modelo
                    }
                } catch (e: Exception) {
                    if (retryCount < maxRetries) {
                        retryCount++
                        kotlinx.coroutines.delay(1500L * retryCount)
                        continue
                    }
                    Log.e("GeminiRepo", "Exception en $modelName: ${e.message}")
                    break
                }
            }
            // Si llegamos acá, este modelo falló, el bucle for probará el siguiente
        }

        // Si todos los modelos fallaron
        performOfflineFallback(userQuery, parkings)
    }

    private fun buildPrompt(
        userQuery: String, 
        parkings: List<ParkingSpot>, 
        userLocation: Coordinates,
        history: List<ChatMessage>
    ): String {
        val parkingsContext = parkings.take(8).joinToString("\n") { p ->
            "- ID: ${p.id}, Nombre: ${p.name}, Dirección: ${p.address}, Distancia: ${p.distance}, Precio: $${p.pricePerHour}/h, Libres: ${p.availableSpots}/${p.totalSpots}, Techado: ${if (p.isCovered) "Sí" else "No"}, Seguridad 24hs: ${if (p.security24hs) "Sí" else "No"}"
        }

        val historyContext = history.takeLast(6).joinToString("\n") { msg ->
            "${if (msg.sender == "user") "Usuario" else "Asistente"}: ${msg.text}"
        }

        return """
            Eres el Asistente IA de "Estacionamiento Inteligente" en Buenos Aires (CABA). 
            Tu objetivo es ayudar al usuario a encontrar la mejor cochera basándote en su ubicación y necesidades.
            
            Ubicación actual del usuario: Lat ${userLocation.lat}, Lng ${userLocation.lng}
            
            Cocheras disponibles cercanas:
            $parkingsContext
            
            Historial de la conversación:
            $historyContext
            
            Instrucciones:
            1. Responde en español rioplatense (usar 'vos', 'che'). SE BREVE Y DIRECTO.
            2. Si el usuario pregunta por precios o disponibilidad, usa los datos.
            3. Invita al usuario a tocar el botón "Ir ahora" debajo.
            4. Incluye los IDs sugeridos al final: [SUGGESTED_IDS: ["id1", "id2"]]
            
            Consulta del usuario: "$userQuery"
        """.trimIndent()
    }

    private fun parseGeminiReply(text: String): ChatMessage {
        val idRegex = """\[SUGGESTED_IDS:\s*\[(.*?)\]\]""".toRegex()
        val match = idRegex.find(text)
        val suggestedIds = match?.groupValues?.get(1)
            ?.split(",")
            ?.map { it.trim().replace("\"", "").replace("'", "") }
            ?.filter { it.isNotBlank() }

        val cleanText = text.replace(idRegex, "").trim()

        return ChatMessage(
            id = "bot_${System.currentTimeMillis()}",
            sender = "bot",
            text = cleanText,
            timestamp = "Ahora",
            suggestedParkings = suggestedIds
        )
    }

    private fun performOfflineFallback(userQuery: String, parkings: List<ParkingSpot>): ChatMessage {
        val query = userQuery.lowercase()
        val suggested = when {
            query.contains("barato") || query.contains("precio") -> parkings.sortedBy { it.pricePerHour }.take(2)
            query.contains("techado") -> parkings.filter { it.isCovered }.take(2)
            query.contains("libre") || query.contains("lugar") -> parkings.sortedByDescending { it.availableSpots }.take(2)
            else -> parkings.take(2)
        }

        val text = if (apiKey.isBlank()) {
            "Parece que no tenés una API Key configurada. Te muestro algunas opciones recomendadas según tu búsqueda:\n" +
            suggested.joinToString("\n") { "• ${it.name} ($${it.pricePerHour}/h)" }
        } else {
            "Tuve un problema para conectarme con Gemini. Aquí tenés algunas opciones cercanas:\n" +
            suggested.joinToString("\n") { "• ${it.name} ($${it.pricePerHour}/h)" }
        }

        return ChatMessage(
            id = "bot_${System.currentTimeMillis()}",
            sender = "bot",
            text = text,
            timestamp = "Ahora",
            suggestedParkings = suggested.map { it.id }
        )
    }
}
