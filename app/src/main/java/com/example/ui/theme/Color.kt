package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF0F0F13)
val DarkNavBackground = Color(0xFF0A0A0E)

val GlassCardBackground = Color(0x0DFFFFFF) // bg-white/5
val GlassCardBackgroundActive = Color(0x1AFFFFFF) // bg-white/10
val GlassBorder = Color(0x1AFFFFFF) // border-white/10
val GlassBorderBright = Color(0x33FFFFFF)

val PrimaryPink = Color(0xFFFF3366)
val SecondaryPurple = Color(0xFF8A2BE2)

val PinkTint = Color(0x33FF3366)
val PurpleTint = Color(0x338A2BE2)

val TextPrimary = Color(0xFFE4E4E6)
val TextSecondary = Color(0x99E4E4E6)
val TextMuted = Color(0x4DE4E4E6)

val GradientPrimary = Brush.horizontalGradient(
    colors = listOf(PrimaryPink, SecondaryPurple)
)

val GradientHeaderGlow = Brush.verticalGradient(
    colors = listOf(SecondaryPurple.copy(alpha = 0.25f), Color.Transparent)
)
