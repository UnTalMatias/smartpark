package com.estacionamiento.inteligente.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class ReportType {
    ACCIDENT,
    TRAFFIC,
    HAZARD,
    ROAD_CLOSED
}

@Serializable
data class CommunityReport(
    val id: String,
    val type: ReportType,
    val lat: Double,
    val lng: Double,
    val description: String,
    val timestamp: Long = System.currentTimeMillis(),
    val reportedBy: String
)
