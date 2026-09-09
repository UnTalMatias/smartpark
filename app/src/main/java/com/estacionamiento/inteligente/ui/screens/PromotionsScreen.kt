package com.estacionamiento.inteligente.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.estacionamiento.inteligente.data.model.PromoItem
import com.estacionamiento.inteligente.ui.theme.GreenPrimary

@Composable
fun PromotionsScreen(
    promos: List<PromoItem>,
    onNavigateBack: () -> Unit,
    onSelectPromo: (String) -> Unit
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
                Text(
                    text = "Promociones",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(promos) { promo ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectPromo(promo.parkingId) },
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 2.dp
                ) {
                    if (promo.isHero) {
                        // Hero Banner
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .background(GreenPrimary)
                        ) {
                            AsyncImage(
                                model = promo.image,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize(),
                                alpha = 0.35f
                            )

                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                            ) {
                                Text(text = promo.discountBadge, fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color.White)
                                Text(text = promo.description, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                Text(text = promo.validUntil, fontSize = 10.sp, color = Color(0xFFDCFCE7))
                            }
                        }
                    } else {
                        // Standard Card
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.size(60.dp)
                            ) {
                                AsyncImage(
                                    model = promo.image,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = promo.parkingName, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                Text(text = promo.discountBadge, fontSize = 13.sp, fontWeight = FontWeight.Black, color = GreenPrimary)
                                Text(text = promo.description, fontSize = 10.sp, color = Color(0xFF64748B))
                            }
                        }
                    }
                }
            }
        }
    }
}
