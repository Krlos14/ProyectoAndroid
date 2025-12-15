package com.example.anitalks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.anitalks.ui.screens.AniTalksApp
import com.example.anitalks.ui.theme.AniTalksTheme
import com.example.anitalks.ui.util.rememberWindowSize
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val splash: SplashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)


        var keepOnScreen by mutableStateOf(true)
        splash.setKeepOnScreenCondition { keepOnScreen }

        setContent {

            LaunchedEffect(Unit) {
                delay(1000)
                keepOnScreen = false
            }


            val windowSize = rememberWindowSize()

            AniTalksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 3. Llamada al Contenedor Principal de la Aplicación (App Shell)
                    // Este componente (AniTalksApp) contiene el Scaffold, la Barra de Navegación
                    // Inferior y toda la lógica de cambio de pantalla (when/currentScreen).
                    AniTalksApp(windowSize = windowSize)
                }
            }
        }
    }
}