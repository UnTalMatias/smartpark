package com.estacionamiento.inteligente.ui.screens

import androidx.compose.foundation.background
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
import com.estacionamiento.inteligente.ui.theme.GreenPrimary

@Composable
fun LiveRouteScreen(
    parking: ParkingSpot,
    distanceKm: Double,
    timeMinutes: Int,
    speedKmh: Int,
    onEndRoute: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Map Canvas
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE2E8F0)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🛣️ Navegación en tiempo real (OSRM + GPS)\nHacia ${parking.name}\n(Manuel Ugarte 2400 ➔ ${parking.address})",
                color = Color(0xFF475569),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        // Top Status Header with Dynamic Stats
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = GreenPrimary,
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onEndRoute, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.White)
                    }
                    Text(
                        text = "Trayecto en vivo",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    IconButton(onClick = { /* Share */ }, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Share, contentDescription = "Compartir", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Stats Banner (Distance, ETA, Speed)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "$distanceKm km", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                            Text(text = "DISTANCIA", fontSize = 8.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        }

                        Divider(modifier = Modifier.height(24.dp).width(1.dp), color = Color(0xFFE2E8F0))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "$timeMinutes min", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                            Text(text = "TIEMPO", fontSize = 8.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        }

                        Divider(modifier = Modifier.height(24.dp).width(1.dp), color = Color(0xFFE2E8F0))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "$speedKmh km/h", fontSize = 14.sp, fontWeight = FontWeight.Black, color = GreenPrimary)
                            Text(text = "VELOCIDAD", fontSize = 8.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Bottom CTA Button: Finalizar Trayecto
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                OutlinedButton(
                    onClick = onEndRoute,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFDC2626)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDC2626))
                ) {
                    Icon(Icons.Default.Cancel, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Finalizar trayecto", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}
