package com.estacionamiento.inteligente.ui.navigation

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "1. Inicio")
    object Map : Screen("map", "2. Mapa")
    object Search : Screen("search", "3. Buscar")
    object ParkingDetail : Screen("parking_detail/{parkingId}", "4. Estacionamiento") {
        fun createRoute(parkingId: String) = "parking_detail/$parkingId"
    }
    object LiveRoute : Screen("live_route/{parkingId}", "5. Trayecto") {
        fun createRoute(parkingId: String) = "live_route/$parkingId"
    }
    object AiAssistant : Screen("ai_assistant", "6. Asistente IA")
    object Membership : Screen("membership", "7. Membresías")
    object Promotions : Screen("promotions", "8. Promociones")
    object MyVehicle : Screen("my_vehicle", "9. Auto / Integración")
    object Apis : Screen("apis", "10. APIs Utilizadas")
}
