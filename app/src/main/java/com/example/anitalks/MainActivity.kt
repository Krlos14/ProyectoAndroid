package com.example.anitalks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anitalks.data.local.AnimeDatabase
import com.example.anitalks.data.preferences.UserPreferencesDataStore
import com.example.anitalks.data.repository.AnimeRepository
import com.example.anitalks.ui.screens.AniTalksApp
import com.example.anitalks.ui.theme.AniTalksTheme
import com.example.anitalks.ui.util.rememberWindowSize
import com.example.anitalks.ui.viewmodel.*
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val splash: SplashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var keepOnScreen by mutableStateOf(true)
        splash.setKeepOnScreenCondition { keepOnScreen }

        val database = AnimeDatabase.getDatabase(this)
        val repository = AnimeRepository(database.animeDao())
        val preferences = UserPreferencesDataStore(this)

        setContent {

            LaunchedEffect(Unit) {
                delay(1000)
                keepOnScreen = false
            }

            val windowSize = rememberWindowSize()

            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileViewModelFactory(preferences)
            )

            val themeMode by profileViewModel.themeMode.collectAsState()

            val animeListViewModel: AnimeListViewModel = viewModel(
                factory = AnimeListViewModelFactory(repository)
            )

            val favoriteViewModel: FavoriteViewModel = viewModel(
                factory = FavoriteViewModelFactory(repository)
            )

            AniTalksTheme(themeMode = themeMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AniTalksApp(
                        windowSize = windowSize,
                        animeListViewModel = animeListViewModel,
                        favoriteViewModel = favoriteViewModel,
                        profileViewModel = profileViewModel,
                        repository = repository
                    )
                }
            }
        }
    }
}