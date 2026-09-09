package com.estacionamiento.inteligente.data.model

data class PromoItem(
    val id: String,
    val parkingId: String,
    val parkingName: String,
    val discountBadge: String,
    val description: String,
    val validUntil: String,
    val image: String,
    val isHero: Boolean = false
)

data class ApiServiceItem(
    val id: String,
    val name: String,
    val description: String,
    val iconType: String,
    val status: String = "active",
    val endpoint: String
)

data class ChatMessage(
    val id: String,
    val sender: String, // "user" or "bot"
    val text: String,
    val timestamp: String,
    val suggestedParkings: List<String>? = null
)
