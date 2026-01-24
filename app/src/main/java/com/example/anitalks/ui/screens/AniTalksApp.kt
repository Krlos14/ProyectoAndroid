package com.example.anitalks.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import com.example.anitalks.R
import com.example.anitalks.data.source.sampleAnimeList
import com.example.anitalks.ui.util.WindowSize

@Composable
fun AniTalksApp(windowSize: WindowSize) {
    var currentRoute by remember { mutableStateOf("list") }
    var animeData by remember { mutableStateOf(sampleAnimeList) }
    var searchQuery by remember { mutableStateOf("") }
    var isLoggedIn by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var animeToUnfav by remember { mutableStateOf<com.example.anitalks.data.model.Anime?>(null) }

    val filtered = animeData.filter { it.title.contains(searchQuery, true) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    animeData = animeData.map { if (it.id == animeToUnfav?.id) it.copy(isFavorite = false) else it }
                    showDeleteDialog = false
                }) { Text(stringResource(R.string.btn_accept)) }
            },
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text(stringResource(R.string.btn_cancel)) } },
            title = { Text(stringResource(R.string.dialog_delete_title)) },
            text = { Text(stringResource(R.string.dialog_delete_msg)) }
        )
    }

    Scaffold(
        bottomBar = {
            if (windowSize == WindowSize.Compact) {
                NavigationBar {
                    NavigationBarItem(currentRoute == "list", { currentRoute = "list" }, { Icon(Icons.Default.Home, null) }, label = { Text("Home") })
                    NavigationBarItem(currentRoute == "fav", { currentRoute = "fav" }, { Icon(Icons.Default.Star, null) }, label = { Text("Favs") })
                    NavigationBarItem(currentRoute == "pro", { currentRoute = "pro" }, { Icon(Icons.Default.Person, null) }, label = { Text("Perfil") })
                }
            }
        }
    ) { p ->
        val mod = Modifier.padding(p)
        when {
            //Lista Animes
            currentRoute == "list" -> {
                if (filtered.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.no_results))
                    }
                } else {
                    ElemListScreen(
                        windowSize = windowSize,
                        animeList = filtered,
                        searchQuery = searchQuery,
                        onSearchChange = { searchQuery = it },
                        onItemClick = { currentRoute = "detail/${it.title}" },
                        onFavoriteClick = { anime ->
                            // Lógica para añadir o quitar favoritos
                            if (anime.isFavorite) {
                                animeToUnfav = anime
                                showDeleteDialog = true
                            } else {
                                animeData = animeData.map {
                                    if (it.id == anime.id) it.copy(isFavorite = true) else it
                                }
                            }
                        },
                        onAboutClick = { currentRoute = "about" },
                        modifier = mod
                    )
                }
            }


            currentRoute == "fav" -> {
                val favorites = animeData.filter { it.isFavorite }
                if (favorites.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.no_favorites))
                    }
                } else {
                    ElemListScreen(
                        windowSize = windowSize,
                        animeList = favorites,
                        searchQuery = "",
                        onSearchChange = {},
                        onItemClick = { currentRoute = "detail/${it.title}" },
                        onFavoriteClick = { animeToUnfav = it; showDeleteDialog = true },
                        onAboutClick = { currentRoute = "about" },
                        modifier = mod
                    )
                }
            }


            currentRoute.startsWith("detail/") -> {
                val animeTitle = currentRoute.substringAfter("/")
                val anime = animeData.find { it.title == animeTitle }
                if (anime != null) {
                    DetailItemScreen(
                        windowSize = windowSize,
                        anime = anime,
                        onBackClick = { currentRoute = "list" },
                        modifier = mod
                    )
                }
            }

            //Perfil
            currentRoute == "pro" -> {
                ProfileScreen(
                    isLoggedIn = isLoggedIn,
                    onLoginToggle = { isLoggedIn = !isLoggedIn },
                    modifier = mod
                )
            }

           //Sobre de
            currentRoute == "about" -> {
                AboutScreen(
                    onBackClick = { currentRoute = "list" },
                    modifier = mod
                )
            }
        }
        }
    }
