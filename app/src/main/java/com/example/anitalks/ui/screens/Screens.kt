package com.example.anitalks.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.anitalks.data.local.CommentEntity
import com.example.anitalks.data.model.Anime
import com.example.anitalks.ui.components.AnimeItemCard
import com.example.anitalks.ui.util.WindowSize
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElemListScreen(
    windowSize: WindowSize,
    animeList: List<Anime>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onItemClick: (Anime) -> Unit,
    onFavoriteClick: (Anime) -> Unit,
    onAboutClick: () -> Unit,
    isLoading: Boolean = false,
    emptyMessage: String = "No hay resultados",
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Lista de animes") },
                    actions = {
                        IconButton(onClick = onAboutClick) {
                            Icon(Icons.Default.Info, contentDescription = null)
                        }
                    }
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = { Text("Buscar anime") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    singleLine = true
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            animeList.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(emptyMessage)
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(
                        if (windowSize == WindowSize.Compact) 1 else 2
                    ),
                    contentPadding = paddingValues
                ) {
                    items(animeList) { anime ->
                        AnimeItemCard(
                            anime = anime,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { onItemClick(anime) },
                            onFavoriteClick = { onFavoriteClick(anime) }
                        )
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailItemScreen(
    windowSize: WindowSize,
    anime: Anime,
    comments: List<CommentEntity>,
    username: String,
    isFavorite: Boolean, // <--- Recibimos el estado de favorito
    onAddComment: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showCommentDialog by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }

    // El diálogo de comentario se mantiene igual
    if (showCommentDialog) {
        AlertDialog(
            onDismissRequest = {
                showCommentDialog = false
                commentText = ""
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (commentText.isNotBlank()) {
                            onAddComment(commentText)
                            commentText = ""
                            showCommentDialog = false
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showCommentDialog = false
                    commentText = ""
                }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Nuevo comentario") },
            text = {
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    label = { Text("Comentario") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(anime.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        // --- CAMBIO 1: El botón flotante solo existe si es favorito ---
        floatingActionButton = {
            if (isFavorite) {
                FloatingActionButton(
                    onClick = { showCommentDialog = true }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir comentario")
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            // ... (Toda la sección de imagen y detalles del anime se mantiene igual)
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = anime.title,
                modifier = Modifier.fillMaxWidth().height(280.dp).padding(bottom = 16.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(
                    text = "Puntuación: ${anime.score}/10",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Episodios: ${anime.episodes ?: "Desconocido"}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Estado: ${anime.status ?: "Desconocido"}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Año: ${anime.year ?: "Desconocido"}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Descripción", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(text = anime.synopsis, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 8.dp))
            Spacer(modifier = Modifier.height(24.dp))

            // --- SECCIÓN DE COMENTARIOS ---
            Text(text = "Comentarios", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)

            // --- CAMBIO 2: Mensaje de aviso si no es favorito ---
            if (!isFavorite) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = "Solo puedes comentar si añades este anime a tus favoritos.",
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (comments.isEmpty()) {
                Text(
                    text = "Todavía no hay comentarios para este anime.",
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                comments.forEach { comment ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = comment.user, fontWeight = FontWeight.Bold)
                            Text(text = comment.text, modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    username: String,
    themeMode: String,
    onUsernameChange: (String) -> Unit,
    onThemeChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var usernameText by remember(username) {
        mutableStateOf(username)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val themeText = when (themeMode) {
        "light" -> "Claro"
        "dark" -> "Oscuro"
        else -> "Según el sistema"
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Perfil de usuario",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = usernameText,
            onValueChange = {
                usernameText = it
            },
            label = {
                Text("Nombre de usuario")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (usernameText.isNotBlank()) {
                    onUsernameChange(usernameText)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar usuario")
        }

        Spacer(modifier = Modifier.height(24.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = themeText,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Tema de la aplicación")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text("Según el sistema")
                    },
                    onClick = {
                        onThemeChange("system")
                        expanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("Claro")
                    },
                    onClick = {
                        onThemeChange("light")
                        expanded = false
                    }
                )

                DropdownMenuItem(
                    text = {
                        Text("Oscuro")
                    },
                    onClick = {
                        onThemeChange("dark")
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun AboutScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("AniTalks 1.0")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBackClick) {
            Text("Volver")
        }
    }
}