package com.estacionamiento.inteligente.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.estacionamiento.inteligente.ui.screens.*
import com.estacionamiento.inteligente.viewmodel.ParkingViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: ParkingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToSearch = { navController.navigate(Screen.Search.route) },
                onNavigateToMap = { navController.navigate(Screen.Map.route) },
                onNavigateToAi = { navController.navigate(Screen.AiAssistant.route) },
                onNavigateToMemberships = { navController.navigate(Screen.Membership.route) },
                onNavigateToVehicle = { navController.navigate(Screen.MyVehicle.route) },
                onNavigateToPromos = { navController.navigate(Screen.Promotions.route) }
            )
        }

        composable(Screen.Map.route) {
            MapScreen(
                parkings = uiState.parkings,
                reports = uiState.reports,
                selectedParking = uiState.selectedParking,
                onSelectParking = { viewModel.selectParking(it) },
                onNavigateToDetail = { parkingId ->
                    navController.navigate(Screen.ParkingDetail.createRoute(parkingId))
                },
                onNavigateToSearch = { navController.navigate(Screen.Search.route) },
                onNavigateToHome = { navController.navigate(Screen.Home.route) },
                onReportIncident = { type, desc, lat, lng ->
                    viewModel.reportIncident(type, desc, lat, lng)
                }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                parkings = uiState.parkings,
                activeFilter = uiState.activeFilter,
                onFilterChange = { viewModel.setFilter(it) },
                onNavigateBack = { navController.popBackStack() },
                onSelectParking = { parking ->
                    viewModel.selectParking(parking)
                    navController.navigate(Screen.ParkingDetail.createRoute(parking.id))
                }
            )
        }

        composable(Screen.ParkingDetail.route) { backStackEntry ->
            val parkingId = backStackEntry.arguments?.getString("parkingId")
            val parking = uiState.parkings.find { it.id == parkingId } ?: uiState.selectedParking
            parking?.let {
                ParkingDetailScreen(
                    parking = it,
                    onNavigateBack = { navController.popBackStack() },
                    onStartRoute = { navController.navigate(Screen.LiveRoute.createRoute(it.id)) }
                )
            }
        }

        composable(Screen.LiveRoute.route) { backStackEntry ->
            val parkingId = backStackEntry.arguments?.getString("parkingId")
            val parking = uiState.parkings.find { it.id == parkingId } ?: uiState.selectedParking
            parking?.let {
                LiveRouteScreen(
                    parking = it,
                    distanceKm = uiState.remainingDistanceKm,
                    timeMinutes = uiState.remainingTimeMinutes,
                    speedKmh = uiState.currentSpeedKmh,
                    onEndRoute = { navController.navigate(Screen.Map.route) }
                )
            }
        }

        composable(Screen.AiAssistant.route) {
            AiAssistantScreen(
                messages = uiState.chatMessages,
                onSendMessage = { viewModel.sendChatMessage(it) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Membership.route) {
            MembershipScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(Screen.Promotions.route) {
            PromotionsScreen(
                promos = com.estacionamiento.inteligente.data.datasource.MockDataSource.PROMOS,
                onNavigateBack = { navController.popBackStack() },
                onSelectPromo = { parkingId ->
                    navController.navigate(Screen.ParkingDetail.createRoute(parkingId))
                }
            )
        }

        composable(Screen.MyVehicle.route) {
            MyVehicleScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(Screen.Apis.route) {
            ApisScreen(
                apis = com.estacionamiento.inteligente.data.datasource.MockDataSource.APIS,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
