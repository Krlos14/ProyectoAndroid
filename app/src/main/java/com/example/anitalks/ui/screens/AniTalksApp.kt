package com.example.anitalks.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.Icons
import com.example.anitalks.R
import com.example.anitalks.data.model.Anime
import com.example.anitalks.data.source.sampleAnimeList
import com.example.anitalks.ui.util.WindowSize


sealed class Screen {
    object List : Screen()
    data class Detail(val animeId: Int) : Screen()
    object FavList : Screen()
    object Profile : Screen()
    object About : Screen()
}

data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector,
    val labelResId: Int
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.List, Icons.Default.Home, R.string.nav_home), // Lista de Animes
    BottomNavItem(Screen.FavList, Icons.Default.Star, R.string.nav_favorites), // Favoritos
    BottomNavItem(Screen.Profile, Icons.Default.Person, R.string.nav_profile), // Perfil
    BottomNavItem(Screen.About, Icons.Default.Info, R.string.nav_info) // Acerca de
)


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AniTalksApp(
    windowSize: WindowSize
) {

    var currentScreen by remember { mutableStateOf<Screen>(Screen.List) }
    var animeData by remember { mutableStateOf(sampleAnimeList) }


    val favoriteAnimeData = remember(animeData) {
        animeData.filter { it.isFavorite }
    }


    val onFavoriteToggle: (Anime) -> Unit = { updatedAnime ->
        animeData = animeData.map {

            if (it.id == updatedAnime.id) it.copy(isFavorite = !it.isFavorite) else it
        }
    }


    Scaffold(
        bottomBar = {

            if (windowSize == WindowSize.Compact) {
                AniTalksBottomBar(
                    currentScreen = currentScreen,
                    onItemSelected = { screen -> currentScreen = screen }
                )
            }
        }
    ) { paddingValues ->

        val modifier = Modifier.padding(paddingValues)

        when (val screen = currentScreen) {


            Screen.List -> ElemListScreen(
                windowSize = windowSize,
                animeList = animeData,
                onItemClick = { anime ->
                    currentScreen = Screen.Detail(anime.id)
                },
                onFavoriteToggle = onFavoriteToggle,
                onAboutClick = { currentScreen = Screen.About },
                modifier = modifier,
                listTitle = stringResource(R.string.title_anime_list)
            )


            is Screen.Detail -> animeData.find { it.id == screen.animeId }?.let { anime ->
                DetailItemScreen(
                    windowSize = windowSize,
                    anime = anime,
                    onFavoriteToggle = onFavoriteToggle,

                    onBackClick = { currentScreen = Screen.List },
                    modifier = modifier
                )
            }


            Screen.FavList -> FavListScreen(
                windowSize = windowSize,
                favoriteAnimeList = favoriteAnimeData,
                onItemClick = { anime ->
                    currentScreen = Screen.Detail(anime.id)
                },
                onFavoriteToggle = onFavoriteToggle,
                onAboutClick = { currentScreen = Screen.About },
                modifier = modifier
            )


            Screen.Profile -> ProfileScreen(
                modifier = modifier
            )


            Screen.About -> AboutScreen(
                onEmailClick = { /* Lógica de email */ },
                onBackClick = { currentScreen = Screen.List },
                modifier = modifier
            )
        }
    }
}

@Composable
fun AniTalksBottomBar(
    currentScreen: Screen,
    onItemSelected: (Screen) -> Unit
) {
    NavigationBar {
        bottomNavItems.forEach { item ->

            val selected = item.screen::class == currentScreen::class
            NavigationBarItem(
                selected = selected,
                onClick = { onItemSelected(item.screen) },
                icon = { Icon(item.icon, contentDescription = stringResource(item.labelResId)) },
                label = { Text(stringResource(item.labelResId)) }
            )
        }
    }
}