package com.example.anitalks.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

/**
 * Define las clases de tamaño canónico de la ventana según el ancho de la pantalla.
 * Usamos los puntos de ruptura estándar de Material Design:
 * - Compacto: < 600 dp (Móvil)
 * - Medio: 600 dp a < 840 dp (Tablet/Plegables)
 * - Expandido: >= 840 dp (Escritorio/Tablet grande)
 */
enum class WindowSize { Compact, Medium, Expanded }

/**
 * Composible que devuelve el tamaño de la ventana actual basado en el ancho de la pantalla.
 */
@Composable
@ReadOnlyComposable
fun rememberWindowSize(): WindowSize {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp


    return when {
        screenWidth < 600.dp -> WindowSize.Compact
        screenWidth < 840.dp -> WindowSize.Medium
        else -> WindowSize.Expanded
    }
}