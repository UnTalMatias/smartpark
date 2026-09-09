package com.estacionamiento.inteligente.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Espeja la tabla `parkings` de Supabase (cocheras reales de CABA).
 * Se separa del modelo de UI porque la DB no trae datos de rating,
 * fotos, horarios ni métodos de pago: eso lo resuelve [toParkingSpot].
 */
@Serializable
data class ParkingRow(
    val id: String? = null,
    val name: String? = null,
    val address: String? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    @SerialName("available_spots") val availableSpots: Int? = null,
    @SerialName("total_spots") val totalSpots: Int? = null,
    @SerialName("price_per_hour") val pricePerHour: Int? = null,
    @SerialName("is_covered") val isCovered: Boolean? = null,
    val type: String? = null
)

private const val DEFAULT_PARKING_PHOTO =
    "https://images.unsplash.com/photo-1590674899484-d5640e854abe?auto=format&fit=crop&w=800&q=80"

fun ParkingRow.toParkingSpot(origin: Coordinates): ParkingSpot {
    val pLat = lat ?: 0.0
    val pLng = lng ?: 0.0
    val distMeters = haversineMeters(origin.lat, origin.lng, pLat, pLng)
    val type = type

    val resolvedName = when {
        !name.isNullOrBlank() -> name
        type == "cochera" -> "Cochera"
        else -> "Estacionamiento"
    }

    return ParkingSpot(
        id = id ?: "row-$pLat-$pLng",
        name = resolvedName,
        address = address ?: "",
        lat = pLat,
        lng = pLng,
        distance = formatDistance(distMeters),
        walkTime = "${((distMeters / 80.0).roundToInt().coerceAtLeast(1))} min caminando",
        availableSpots = availableSpots ?: 0,
        totalSpots = totalSpots ?: 0,
        pricePerHour = pricePerHour ?: 0,
        isCovered = isCovered ?: false,
        security24hs = false,
        rating = 0.0,
        reviewCount = 0,
        hours = "Consultar",
        photos = listOf(DEFAULT_PARKING_PHOTO),
        paymentMethods = listOf("efectivo", "tarjeta", "mercadopago"),
        neighborhood = ""
    )
}

fun distanceMeters(origin: Coordinates, spot: ParkingSpot): Double =
    haversineMeters(origin.lat, origin.lng, spot.lat, spot.lng)

private fun haversineMeters(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
    val earthRadius = 6371000.0
    val dLat = Math.toRadians(lat2 - lat1)
    val dLng = Math.toRadians(lng2 - lng1)
    val a = sin(dLat / 2) * sin(dLat / 2) +
        cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLng / 2) * sin(dLng / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return earthRadius * c
}

private fun formatDistance(meters: Double): String {
    if (meters < 1000) return "${meters.roundToInt()} m"
    val rounded = (meters / 100.0).roundToInt() / 10.0
    return if (rounded == rounded.toLong().toDouble()) "${rounded.toLong()} km" else "$rounded km"
}
