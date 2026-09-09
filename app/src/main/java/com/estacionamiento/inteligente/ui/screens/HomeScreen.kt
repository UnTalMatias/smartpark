package com.estacionamiento.inteligente.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.estacionamiento.inteligente.ui.theme.GreenPrimary

@Composable
fun HomeScreen(
    onNavigateToSearch: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToAi: () -> Unit,
    onNavigateToMemberships: () -> Unit,
    onNavigateToVehicle: () -> Unit,
    onNavigateToPromos: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenPrimary)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ){}

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "ESTACIONAMIENTO\nINTELIGENTE",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Encontrá tu lugar.\nAhorrá tiempo.",
                color = Color(0xFFDCFCE7),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }

        // 5 Main Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MenuButton(
                icon = Icons.Default.Search,
                label = "Buscar estacionamiento",
                onClick = onNavigateToSearch
            )
            MenuButton(
                icon = Icons.Default.LocationOn,
                label = "Mi ubicación (GPS)",
                onClick = onNavigateToMap
            )
            MenuButton(
                icon = Icons.Default.SmartToy,
                label = "Asistente IA",
                onClick = onNavigateToAi
            )
            MenuButton(
                icon = Icons.Default.Star,
                label = "Membresías",
                onClick = onNavigateToMemberships
            )
            MenuButton(
                icon = Icons.Default.DirectionsCar,
                label = "Mis vehículos",
                onClick = onNavigateToVehicle
            )
        }

        // Footer status indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4ADE80))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "GPS en tiempo real (ET N°21)",
                    color = Color(0xFFDCFCE7),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            TextButton(onClick = onNavigateToPromos) {
                Text(
                    text = "🏷️ Promos",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MenuButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = GreenPrimary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = label,
                color = Color(0xFF0F172A),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}
