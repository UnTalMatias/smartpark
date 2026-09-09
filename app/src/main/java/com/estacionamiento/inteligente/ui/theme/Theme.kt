package com.estacionamiento.inteligente.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    primaryContainer = GreenLight,
    secondary = BlueAccent,
    background = Color(0xFFF8FAFC),
    surface = Color.White,
    onSurface = TextPrimary
)

@Composable
fun EstacionamientoInteligenteTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
