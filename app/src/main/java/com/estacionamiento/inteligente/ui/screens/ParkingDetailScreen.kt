package com.estacionamiento.inteligente.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.estacionamiento.inteligente.data.model.ParkingSpot
import com.estacionamiento.inteligente.ui.theme.GreenPrimary

@Composable
fun ParkingDetailScreen(
    parking: ParkingSpot,
    onNavigateBack: () -> Unit,
    onStartRoute: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = GreenPrimary
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Atrás", tint = Color.White)
                }
                Text(
                    text = "Estacionamiento",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        // Photo Carousel Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color(0xFF0F172A))
        ) {
            AsyncImage(
                model = parking.photos.firstOrNull(),
                contentDescription = parking.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Information Details
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = parking.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = if (parking.rating > 0.0) "${parking.rating}" else "Nuevo", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = parking.address, fontSize = 11.sp, color = Color(0xFF64748B))

                Spacer(modifier = Modifier.height(16.dp))

                // 4 Grid Feature Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DetailBadge(icon = Icons.Default.DirectionsCar, value = "${parking.availableSpots}", label = "Disponibles", modifier = Modifier.weight(1f))
                    DetailBadge(icon = Icons.Default.AttachMoney, value = "$${parking.pricePerHour}", label = "por hora", modifier = Modifier.weight(1f))
                    DetailBadge(icon = Icons.Default.Roofing, value = if (parking.isCovered) "Techado" else "Abierto", label = "Cobertura", modifier = Modifier.weight(1f))
                    DetailBadge(icon = Icons.Default.Shield, value = "Seguridad", label = "24 hs", modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Horarios", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                Text(text = parking.hours, fontSize = 11.sp, color = Color(0xFF475569))

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Formas de pago", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                Row(
                    modifier = Modifier.padding(top = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PaymentChip("💵 Efectivo")
                    PaymentChip("💳 Tarjeta")
                    PaymentChip("📱 Mercado Pago")
                }
            }

            // Bottom CTA Button
            Button(
                onClick = onStartRoute,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text(
                    text = "Iniciar trayecto",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun DetailBadge(icon: ImageVector, value: String, label: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8FAFC),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = GreenPrimary, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            Text(text = label, fontSize = 8.sp, color = Color(0xFF64748B))
        }
    }
}

@Composable
fun PaymentChip(label: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF1F5F9)
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF334155),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
