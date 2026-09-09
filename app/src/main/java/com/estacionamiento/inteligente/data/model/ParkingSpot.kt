package com.estacionamiento.inteligente.data.model

data class ParkingSpot(
    val id: String,
    val name: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val distance: String,
    val walkTime: String,
    val availableSpots: Int,
    val totalSpots: Int,
    val pricePerHour: Int,
    val isCovered: Boolean,
    val security24hs: Boolean,
    val rating: Double,
    val reviewCount: Int,
    val hours: String,
    val photos: List<String>,
    val paymentMethods: List<String>,
    val pinType: String = "primary",
    val neighborhood: String,
    val hasPromo: Boolean = false,
    val promoText: String? = null,
    val isPaid: Boolean = false,
    val reportCount: Int = 0
)

data class Coordinates(
    val lat: Double,
    val lng: Double
)

data class RouteInfo(
    val destination: ParkingSpot,
    val distanceKm: Double,
    val timeMinutes: Int,
    val coordinates: List<Coordinates>
)
