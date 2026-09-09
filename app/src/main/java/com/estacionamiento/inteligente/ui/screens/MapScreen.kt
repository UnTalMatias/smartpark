package com.estacionamiento.inteligente.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.estacionamiento.inteligente.data.model.ParkingSpot
import com.estacionamiento.inteligente.data.model.CommunityReport
import com.estacionamiento.inteligente.data.model.ReportType
import com.estacionamiento.inteligente.ui.theme.GreenPrimary

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import android.content.Intent
import android.net.Uri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    parkings: List<ParkingSpot>,
    reports: List<CommunityReport> = emptyList(),
    selectedParking: ParkingSpot?,
    isLoading: Boolean = false,
    onSelectParking: (ParkingSpot) -> Unit,
    onRefresh: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToHome: () -> Unit,
    onReportIncident: (ReportType, String, Double, Double) -> Unit
) {
    val context = LocalContext.current
    var showReportDialog by remember { mutableStateOf(false) }
    
    // Configure OSMDroid
    Configuration.getInstance().load(context, context.getSharedPreferences("osm", 0))

    // Refresh data when entering the screen
    LaunchedEffect(Unit) {
        onRefresh()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // OSMDroid View
        AndroidView(
            factory = { ctx ->
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(17.0)
                    controller.setCenter(GeoPoint(-34.5495, -58.4561)) // E.T. N°21
                }
            },
            update = { mapView ->
                // Clear and Re-add Markers to keep it reactive
                mapView.overlays.clear()

                parkings.forEach { parking ->
                    val marker = Marker(mapView)
                    marker.position = GeoPoint(parking.lat, parking.lng)
                    marker.title = parking.name
                    marker.snippet = "${parking.availableSpots} lugares"
                    
                    if (parking.isPaid) {
                        marker.icon = context.getDrawable(android.R.drawable.ic_notification_overlay) 
                    }

                    marker.setOnMarkerClickListener { m, _ ->
                        onSelectParking(parking)
                        m.showInfoWindow()
                        true
                    }
                    mapView.overlays.add(marker)
                }

                reports.forEach { report ->
                    val marker = Marker(mapView)
                    marker.position = GeoPoint(report.lat, report.lng)
                    marker.title = report.type.name
                    marker.snippet = report.description
                    marker.icon = context.getDrawable(android.R.drawable.stat_notify_error)
                    mapView.overlays.add(marker)
                }

                mapView.invalidate()
            },
            modifier = Modifier.fillMaxSize()
        )

        // Loading indicator
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 80.dp), // Under the search bar
                color = GreenPrimary
            )
        }

        // Top Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onNavigateToHome,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color(0xFF334155))
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .clickable { onNavigateToSearch() },
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Buscar dirección o lugar",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Default.Tune,
                        contentDescription = "Filtros",
                        tint = GreenPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Floating Action Buttons (GPS & Compass)
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 120.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FloatingActionButton(
                onClick = { showReportDialog = true },
                containerColor = Color.Red,
                contentColor = Color.White,
                modifier = Modifier.size(44.dp)
            ) {
                Icon(Icons.Default.Report, contentDescription = "Reportar")
            }
            
            FloatingActionButton(
                onClick = onRefresh,
                containerColor = Color.White,
                contentColor = GreenPrimary,
                modifier = Modifier.size(44.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = GreenPrimary)
                } else {
                    Icon(Icons.Default.MyLocation, contentDescription = "GPS")
                }
            }
        }

        if (showReportDialog) {
            AlertDialog(
                onDismissRequest = { showReportDialog = false },
                title = { Text("Reportar Incidente") },
                text = {
                    Column {
                        ReportType.entries.forEach { type ->
                            TextButton(
                                onClick = {
                                    onReportIncident(type, "Reporte de usuario", -34.5495, -58.4561)
                                    showReportDialog = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(type.name, color = Color.Black)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showReportDialog = false }) {
                        Text("Cerrar")
                    }
                }
            )
        }

        // Bottom Sheet Card: Cerca de tu ubicación
        selectedParking?.let { parking ->
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clickable { onNavigateToDetail(parking.id) },
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cerca de tu ubicación (E.T. N°21)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF334155)
                        )
                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "Ver más",
                            tint = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(GreenPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "P",
                                color = Color.White,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = parking.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = parking.address,
                                fontSize = 10.sp,
                                color = Color(0xFF64748B)
                            )
                            Text(
                                text = "${parking.distance} • ${parking.walkTime}",
                                fontSize = 10.sp,
                                color = Color(0xFF334155),
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${parking.availableSpots}",
                                color = GreenPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "lugares",
                                fontSize = 9.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    val gmmIntentUri = Uri.parse("google.navigation:q=${parking.lat},${parking.lng}")
                                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                    mapIntent.setPackage("com.google.android.apps.maps")
                                    context.startActivity(mapIntent)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                modifier = Modifier.height(28.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Navegar", fontSize = 10.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
