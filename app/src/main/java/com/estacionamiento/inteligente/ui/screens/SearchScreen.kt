package com.estacionamiento.inteligente.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.estacionamiento.inteligente.ui.theme.GreenPrimary

@Composable
fun SearchScreen(
    parkings: List<ParkingSpot>,
    activeFilter: String,
    isLoading: Boolean = false,
    onRefresh: () -> Unit,
    onFilterChange: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onSelectParking: (ParkingSpot) -> Unit
) {
    val filters = listOf("cercanos" to "Cercanos", "precio" to "Precio", "disponibles" to "Disponibles", "techados" to "Techados")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {
        // Top Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    IconButton(onClick = onNavigateBack, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = "Atrás")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Buscar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                }

                // Search Input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF1F5F9)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Buscar dirección o lugar", color = Color.Gray, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = onRefresh,
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(GreenPrimary)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(Icons.Default.Refresh, contentDescription = "Refrescar", tint = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Filter Chips
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filters) { (id, label) ->
                        val isSelected = activeFilter == id
                        Surface(
                            modifier = Modifier.clickable { onFilterChange(id) },
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) GreenPrimary else Color.White,
                            border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)) else null
                        ) {
                            Text(
                                text = label,
                                color = if (isSelected) Color.White else Color(0xFF475569),
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // Parkings List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = "Estacionamientos cercanos (${parkings.size})",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            items(parkings) { parking ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectParking(parking) },
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(GreenPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("P", color = Color.White, fontWeight = FontWeight.Black, fontSize = 18.sp)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(parking.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF0F172A))
                            Text(parking.address, fontSize = 10.sp, color = Color(0xFF64748B))
                            Text("${parking.distance} • ${parking.walkTime}", fontSize = 10.sp, color = Color(0xFF334155), fontWeight = FontWeight.Medium)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text("${parking.availableSpots}", color = GreenPrimary, fontWeight = FontWeight.Black, fontSize = 18.sp)
                            Text("lugares", fontSize = 9.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}
