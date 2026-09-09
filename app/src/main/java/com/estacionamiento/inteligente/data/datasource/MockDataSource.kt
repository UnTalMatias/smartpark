package com.estacionamiento.inteligente.data.datasource

import com.estacionamiento.inteligente.data.model.ApiServiceItem
import com.estacionamiento.inteligente.data.model.ChatMessage
import com.estacionamiento.inteligente.data.model.Coordinates
import com.estacionamiento.inteligente.data.model.ParkingSpot
import com.estacionamiento.inteligente.data.model.PromoItem

object MockDataSource {

    // Ubicación real Escuela Técnica N° 21 "Fragata Libertad" (Manuel Ugarte 2400, Nuñez)
    val SCHOOL_COORDINATES = Coordinates(lat = -34.5512, lng = -58.4608)

    val INITIAL_PARKINGS = listOf(
        ParkingSpot(
            id = "plaza",
            name = "Estacionamiento Plaza Ugarte",
            address = "Manuel Ugarte 2450 (frente a ET 21), Nuñez",
            lat = -34.5508,
            lng = -58.4615,
            distance = "120 m",
            walkTime = "4 min caminando",
            availableSpots = 15,
            totalSpots = 45,
            pricePerHour = 350,
            isCovered = true,
            security24hs = true,
            rating = 4.6,
            reviewCount = 128,
            hours = "Lun - Dom 24 horas",
            photos = listOf(
                "https://images.unsplash.com/photo-1590674899484-d5640e854abe?auto=format&fit=crop&w=800&q=80",
                "https://images.unsplash.com/photo-1506521781263-d8422e82f27a?auto=format&fit=crop&w=800&q=80"
            ),
            paymentMethods = listOf("efectivo", "tarjeta", "mercadopago"),
            pinType = "primary",
            neighborhood = "Nuñez",
            hasPromo = true,
            promoText = "15% OFF"
        ),
        ParkingSpot(
            id = "recoleta",
            name = "Parking Cabildo & Congreso",
            address = "Av. Cabildo 2820, Nuñez / Belgrano",
            lat = -34.5535,
            lng = -58.4580,
            distance = "350 m",
            walkTime = "5 min caminando",
            availableSpots = 8,
            totalSpots = 30,
            pricePerHour = 400,
            isCovered = true,
            security24hs = true,
            rating = 4.8,
            reviewCount = 95,
            hours = "Lun - Dom 24 horas",
            photos = listOf(
                "https://images.unsplash.com/photo-1506521781263-d8422e82f27a?auto=format&fit=crop&w=800&q=80"
            ),
            paymentMethods = listOf("efectivo", "tarjeta", "mercadopago"),
            pinType = "primary",
            neighborhood = "Nuñez",
            hasPromo = true,
            promoText = "10% OFF"
        ),
        ParkingSpot(
            id = "centro",
            name = "Estacionamiento Cuba Central",
            address = "Cuba 2950, Nuñez",
            lat = -34.5480,
            lng = -58.4630,
            distance = "550 m",
            walkTime = "7 min caminando",
            availableSpots = 22,
            totalSpots = 60,
            pricePerHour = 300,
            isCovered = true,
            security24hs = true,
            rating = 4.4,
            reviewCount = 210,
            hours = "Lun - Vie 07:00 a 23:00 / Sáb 08:00 a 20:00",
            photos = listOf(
                "https://images.unsplash.com/photo-1573348722427-f1d6819fdf98?auto=format&fit=crop&w=800&q=80"
            ),
            paymentMethods = listOf("efectivo", "tarjeta", "mercadopago"),
            pinType = "primary",
            neighborhood = "Nuñez",
            hasPromo = true,
            promoText = "2x1"
        ),
        ParkingSpot(
            id = "tribunales",
            name = "Parking Av. Monroe & Cramer",
            address = "Av. Monroe 2700, Belgrano R",
            lat = -34.5560,
            lng = -58.4645,
            distance = "700 m",
            walkTime = "8 min caminando",
            availableSpots = 10,
            totalSpots = 40,
            pricePerHour = 380,
            isCovered = true,
            security24hs = true,
            rating = 4.3,
            reviewCount = 64,
            hours = "Lun - Dom 24 horas",
            photos = listOf(
                "https://images.unsplash.com/photo-1617814076367-b759c7d7e738?auto=format&fit=crop&w=800&q=80"
            ),
            paymentMethods = listOf("efectivo", "tarjeta"),
            pinType = "primary",
            neighborhood = "Belgrano"
        ),
        ParkingSpot(
            id = "alto_palermo",
            name = "Garage Libertador & Nuñez",
            address = "Av. del Libertador 7100, Nuñez",
            lat = -34.5460,
            lng = -58.4550,
            distance = "850 m",
            walkTime = "2 min caminando",
            availableSpots = 18,
            totalSpots = 120,
            pricePerHour = 450,
            isCovered = true,
            security24hs = true,
            rating = 4.7,
            reviewCount = 340,
            hours = "Lun - Dom 24 horas",
            photos = listOf(
                "https://images.unsplash.com/photo-1590674899484-d5640e854abe?auto=format&fit=crop&w=800&q=80"
            ),
            paymentMethods = listOf("efectivo", "tarjeta", "mercadopago"),
            pinType = "secondary",
            neighborhood = "Nuñez"
        )
    )

    val PROMOS = listOf(
        PromoItem(
            id = "promo_hero",
            parkingId = "plaza",
            parkingName = "Estacionamiento Plaza Ugarte (ET 21)",
            discountBadge = "15% OFF",
            description = "en Estacionamiento Plaza",
            validUntil = "Válido hasta 31/05",
            image = "https://images.unsplash.com/photo-1590674899484-d5640e854abe?auto=format&fit=crop&w=800&q=80",
            isHero = true
        ),
        PromoItem(
            id = "promo_recoleta",
            parkingId = "recoleta",
            parkingName = "Parking Cabildo & Congreso",
            discountBadge = "10% OFF",
            description = "En estadías superiores a 3 horas",
            validUntil = "Válido hasta 30/05",
            image = "https://images.unsplash.com/photo-1506521781263-d8422e82f27a?auto=format&fit=crop&w=800&q=80"
        ),
        PromoItem(
            id = "promo_centro",
            parkingId = "centro",
            parkingName = "Estacionamiento Cuba Central",
            discountBadge = "2x1",
            description = "De lunes a viernes de 8 a 18 hs",
            validUntil = "Válido todo el mes",
            image = "https://images.unsplash.com/photo-1573348722427-f1d6819fdf98?auto=format&fit=crop&w=800&q=80"
        )
    )

    val APIS = listOf(
        ApiServiceItem("osm", "OpenStreetMap", "Mapas y visualización vectorial", "osm", "active", "https://tile.openstreetmap.org"),
        ApiServiceItem("nominatim", "Nominatim", "Geocodificación y búsqueda de calles", "nominatim", "active", "https://nominatim.openstreetmap.org/search"),
        ApiServiceItem("overpass", "Overpass API", "Búsqueda de cocheras en tiempo real", "overpass", "active", "https://overpass-api.de/api/interpreter"),
        ApiServiceItem("osrm", "OSRM", "Cálculo de trayectos y rutas óptimas", "osrm", "active", "https://router.project-osrm.org/route/v1/driving"),
        ApiServiceItem("geo", "Geolocation API / FusedLocation", "GPS en tiempo real", "geo", "active", "FusedLocationProviderClient"),
        ApiServiceItem("openai", "Gemini / AI Assistant", "Asistente inteligente vehicular", "openai", "active", "Google Gemini Flash"),
        ApiServiceItem("mp", "Mercado Pago SDK", "Cobro y suscripción Plus", "mp", "active", "https://api.mercadopago.com")
    )

    val CHAT_HISTORY = listOf(
        ChatMessage("msg_1", "bot", "¡Hola! Soy tu asistente inteligente. Puedo ayudarte a encontrar cocheras cerca de la Escuela Técnica N° 21 'Fragata Libertad' (Manuel Ugarte 2400). ¿En qué puedo ayudarte?", "9:41"),
        ChatMessage("msg_2", "user", "Buscame estacionamientos techados cerca de la escuela", "9:42"),
        ChatMessage(
            "msg_3",
            "bot",
            "Encontré estas opciones techadas con seguridad:\n\n1. Estacionamiento Plaza Ugarte (a 120 m)\n2. Parking Cabildo & Congreso (a 350 m)\n3. Estacionamiento Cuba Central (a 550 m)\n\n¿Querés iniciar el trayecto?",
            "9:42",
            listOf("plaza", "recoleta", "centro")
        )
    )
}
