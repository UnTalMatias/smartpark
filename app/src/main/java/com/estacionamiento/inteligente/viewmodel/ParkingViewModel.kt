package com.estacionamiento.inteligente.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estacionamiento.inteligente.data.datasource.MockDataSource
import com.estacionamiento.inteligente.data.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import com.estacionamiento.inteligente.data.remote.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

private const val MAX_PARKINGS = 250

private fun isValidAmbaCoords(lat: Double, lng: Double): Boolean =
    lat in -34.9..-34.4 && lng in -58.8..-58.2

data class UiState(
    val userLocation: Coordinates = MockDataSource.SCHOOL_COORDINATES,
    val parkings: List<ParkingSpot> = MockDataSource.INITIAL_PARKINGS,
    val selectedParking: ParkingSpot? = MockDataSource.INITIAL_PARKINGS.firstOrNull(),
    val favorites: Set<String> = setOf("plaza", "recoleta"),
    val activeFilter: String = "cercanos",
    val searchQuery: String = "",
    val chatMessages: List<ChatMessage> = MockDataSource.CHAT_HISTORY,
    val isNavigating: Boolean = false,
    val remainingDistanceKm: Double = 3.2,
    val remainingTimeMinutes: Int = 12,
    val currentSpeedKmh: Int = 42,
    val isGpsActive: Boolean = true,
    val reports: List<CommunityReport> = emptyList()
)

class ParkingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        fetchData()
        startRealtimeOccupancySimulation()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                val origin = _uiState.value.userLocation
                val rows = SupabaseClient.client.postgrest["parkings"].select().decodeList<ParkingRow>()
                val parkings = rows
                    .filter { it.lat != null && it.lng != null && isValidAmbaCoords(it.lat, it.lng) }
                    .map { it.toParkingSpot(origin) }
                    .sortedBy { distanceMeters(origin, it) }
                    .take(MAX_PARKINGS)

                if (parkings.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        parkings = parkings,
                        selectedParking = parkings.first()
                    )
                }
            } catch (e: Exception) {
                // Mantener mock si falla (offline / DB inalcanzable)
            }
        }
    }

    fun reportIncident(type: ReportType, description: String, lat: Double, lng: Double) {
        viewModelScope.launch {
            val report = CommunityReport(
                id = java.util.UUID.randomUUID().toString(),
                type = type,
                lat = lat,
                lng = lng,
                description = description,
                reportedBy = "User"
            )
            try {
                SupabaseClient.client.postgrest["community_reports"].insert(report)
                fetchData()
            } catch (e: Exception) {
                // Log error
            }
        }
    }

    private fun startRealtimeOccupancySimulation() {
        viewModelScope.launch {
            while (true) {
                delay(6000)
                _uiState.value = _uiState.value.copy(
                    parkings = _uiState.value.parkings.mapIndexed { idx, p ->
                        if (idx == 0) {
                            val delta = if (Math.random() > 0.5) 1 else -1
                            p.copy(availableSpots = (p.availableSpots + delta).coerceIn(1, p.totalSpots.coerceAtLeast(1)))
                        } else p
                    }
                )
            }
        }
    }

    fun selectParking(parking: ParkingSpot) {
        _uiState.value = _uiState.value.copy(selectedParking = parking)
    }

    fun toggleFavorite(parkingId: String) {
        val currentFavs = _uiState.value.favorites.toMutableSet()
        if (currentFavs.contains(parkingId)) currentFavs.remove(parkingId)
        else currentFavs.add(parkingId)
        _uiState.value = _uiState.value.copy(favorites = currentFavs)
    }

    fun setFilter(filter: String) {
        _uiState.value = _uiState.value.copy(activeFilter = filter)
    }

    fun setSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun sendChatMessage(text: String) {
        val userMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            sender = "user",
            text = text,
            timestamp = "9:43"
        )
        val updatedList = _uiState.value.chatMessages + userMsg
        _uiState.value = _uiState.value.copy(chatMessages = updatedList)

        viewModelScope.launch {
            delay(800)
            val botReply = ChatMessage(
                id = "bot_${System.currentTimeMillis()}",
                sender = "bot",
                text = "He verificado las cocheras cercanas a Manuel Ugarte 2400 (ET N°21). Te recomiendo Estacionamiento Plaza Ugarte que cuenta con 15 lugares disponibles.",
                timestamp = "9:43",
                suggestedParkings = listOf("plaza")
            )
            _uiState.value = _uiState.value.copy(chatMessages = _uiState.value.chatMessages + botReply)
        }
    }
}
