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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.estacionamiento.inteligente.ui.theme.GreenPrimary

@Composable
fun MyVehicleScreen(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Atrás")
                }
                Text(text = "Mi Auto", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Integración con tu vehículo", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            Text(text = "Conectá la app con Android Auto para conducir seguro.", fontSize = 11.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(4.dp))

            // Android Auto Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFEFF6FF), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "▲", color = Color(0xFF2563EB), fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Android Auto", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Conectado", fontSize = 10.sp, color = GreenPrimary, fontWeight = FontWeight.Bold)
                        Text(text = "Enviar destinos directo a la pantalla del vehículo", fontSize = 9.sp, color = Color.Gray)
                    }

                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = GreenPrimary)
                }
            }

            // Apple CarPlay Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 1.dp
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFF1F5F9), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🍏", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Apple CarPlay", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(text = "No conectado", fontSize = 10.sp, color = Color.Gray)
                    }

                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
                }
            }

            // Info Box
            Surface(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFDCFCE7),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBBF7D0))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = GreenPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Con la integración en el auto podés ver las indicaciones giro a giro y el mapa sin tocar el celular.",
                        fontSize = 10.sp,
                        color = Color(0xFF14532D),
                        lineHeight = 14.sp
                    )
                }
            }
        }
    }
}
