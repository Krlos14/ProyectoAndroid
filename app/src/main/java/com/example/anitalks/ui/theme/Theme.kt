package com.example.anitalks.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ----------------------------------------------------------------------
// 1. ESQUEMAS DE COLOR COMPLETOS
// ----------------------------------------------------------------------

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF003D51),

    secondary = DarkSecondary,
    onSecondary = Color.Black,

    tertiary = DarkTertiary,

    background = DarkBackground,
    onBackground = Color.White,

    surface = DarkSurface,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF424242)
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB0BEC5),

    secondary = LightSecondary,
    onSecondary = Color.White,

    tertiary = LightTertiary,

    background = LightBackground,
    onBackground = Color.Black,

    surface = LightSurface,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE0E0E0)
)


@Composable
fun AniTalksTheme(
    themeMode: String = "system",
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        "light" -> false
        "dark" -> true
        else -> isSystemInDarkTheme()
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AnimeTalksTypography,
        content = content
    )
}