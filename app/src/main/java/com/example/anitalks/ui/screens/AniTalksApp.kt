package com.example.anitalks.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.anitalks.data.local.FavoriteAnimeEntity
import com.example.anitalks.data.model.Anime
import com.example.anitalks.data.repository.AnimeRepository
import com.example.anitalks.ui.util.WindowSize
import com.example.anitalks.ui.viewmodel.AnimeListViewModel
import com.example.anitalks.ui.viewmodel.DetailViewModel
import com.example.anitalks.ui.viewmodel.DetailViewModelFactory
import com.example.anitalks.ui.viewmodel.FavoriteViewModel
import com.example.anitalks.ui.viewmodel.ProfileViewModel

@Composable
fun AniTalksApp(
    windowSize: WindowSize,
    animeListViewModel: AnimeListViewModel,
    favoriteViewModel: FavoriteViewModel,
    profileViewModel: ProfileViewModel,
    repository: AnimeRepository
) {
    var currentRoute by remember { mutableStateOf("list") }
    var searchQuery by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var favoriteToDelete by remember { mutableStateOf<FavoriteAnimeEntity?>(null) }

    val animeData by animeListViewModel.animeList.collectAsState()
    val isLoading by animeListViewModel.isLoading.collectAsState()
    val message by animeListViewModel.message.collectAsState()
    val favorites by favoriteViewModel.favorites.collectAsState()
    val username by profileViewModel.username.collectAsState()
    val themeMode by profileViewModel.themeMode.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        animeListViewModel.loadAnimeList()
    }

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            animeListViewModel.clearMessage()
        }
    }

    val filtered = animeData.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    val favoriteAnimeList = favorites.map { favorite ->
        Anime(
            id = favorite.id,
            title = favorite.title,
            synopsis = "Anime guardado como favorito en la base de datos local.",
            imageUrl = favorite.imageUrl,
            score = 0f,
            episodes = null,
            status = "Favorito local",
            year = null,
            isFavorite = true
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                favoriteToDelete = null
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        favoriteToDelete?.let {
                            favoriteViewModel.deleteFavorite(it)
                        }
                        showDeleteDialog = false
                        favoriteToDelete = null
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        favoriteToDelete = null
                    }
                ) {
                    Text("Cancelar")
                }
            },
            title = {
                Text("Eliminar favorito")
            },
            text = {
                Text("¿Seguro que quieres eliminar este anime de favoritos?")
            }
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            if (windowSize == WindowSize.Compact) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "list",
                        onClick = { currentRoute = "list" },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Inicio") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == "fav",
                        onClick = { currentRoute = "fav" },
                        icon = { Icon(Icons.Default.Star, contentDescription = null) },
                        label = { Text("Favoritos") }
                    )

                    NavigationBarItem(
                        selected = currentRoute == "pro",
                        onClick = { currentRoute = "pro" },
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("Perfil") }
                    )
                }
            }
        }
    ) { paddingValues ->

        val modifier = Modifier.padding(paddingValues)

        when {
            currentRoute == "list" -> {
                ElemListScreen(
                    windowSize = windowSize,
                    animeList = filtered,
                    searchQuery = searchQuery,
                    onSearchChange = { searchQuery = it },
                    onItemClick = { anime ->
                        currentRoute = "detail/${anime.id}"
                    },
                    onFavoriteClick = { anime ->
                        animeListViewModel.saveFavorite(anime)
                    },
                    onAboutClick = {
                        currentRoute = "about"
                    },
                    isLoading = isLoading,
                    emptyMessage = "No se encontraron animes",
                    modifier = modifier
                )
            }

            currentRoute == "fav" -> {
                if (favoriteAnimeList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No tienes favoritos guardados")
                    }
                } else {
                    ElemListScreen(
                        windowSize = windowSize,
                        animeList = favoriteAnimeList,
                        searchQuery = "",
                        onSearchChange = {},
                        onItemClick = { anime ->
                            currentRoute = "detail/${anime.id}"
                        },
                        onFavoriteClick = { anime ->
                            favoriteToDelete = favorites.find { it.id == anime.id }
                            showDeleteDialog = true
                        },
                        onAboutClick = {
                            currentRoute = "about"
                        },
                        modifier = modifier
                    )
                }
            }

            currentRoute.startsWith("detail/") -> {
                val animeId = currentRoute.substringAfter("/").toIntOrNull()

                val anime = animeData.find { it.id == animeId }
                    ?: favoriteAnimeList.find { it.id == animeId }

                if (anime != null) {
                    val detailViewModel: DetailViewModel = viewModel(
                        key = "detail_${anime.id}",
                        factory = DetailViewModelFactory(
                            repository = repository,
                            animeId = anime.id
                        )
                    )

                    val comments by detailViewModel.comments.collectAsState()

                    // Dentro de AniTalksApp.kt, en la sección del bloque currentRoute == "detail"
                    val favorites by favoriteViewModel.favorites.collectAsState()
                    val isFavorite = favorites.any { it.id == anime.id }// Comprueba si el ID está en favoritos

                    DetailItemScreen(
                        windowSize = windowSize,
                        anime = anime,
                        comments = comments,
                        username = username,
                        isFavorite = isFavorite, // Pasamos este nuevo parámetro
                        onAddComment = { text ->
                            if (isFavorite) { // Doble comprobación de seguridad antes de insertar
                                detailViewModel.addComment(animeId = anime.id, user = username, text = text)
                            }
                        },
                        onBackClick = { currentRoute = "list" },
                        modifier = modifier
                    )
                }
            }

            currentRoute == "pro" -> {
                ProfileScreen(
                    username = username,
                    themeMode = themeMode,
                    onUsernameChange = {
                        profileViewModel.saveUsername(it)
                    },
                    onThemeChange = {
                        profileViewModel.saveThemeMode(it)
                    },
                    modifier = modifier
                )
            }

            currentRoute == "about" -> {
                AboutScreen(
                    onBackClick = {
                        currentRoute = "list"
                    },
                    modifier = modifier
                )
            }
        }
    }
}