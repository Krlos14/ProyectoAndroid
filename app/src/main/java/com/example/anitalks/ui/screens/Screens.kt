package com.example.anitalks.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.anitalks.R
import com.example.anitalks.data.model.Anime
import com.example.anitalks.data.model.Comment
import com.example.anitalks.ui.components.AnimeItemCard
import com.example.anitalks.ui.components.RatingChip
import com.example.anitalks.ui.util.WindowSize
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.text.KeyboardOptions


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AboutScreen(
    onEmailClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_volver)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.app_theme),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.app_desc),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))
            AssistChip(
                onClick = { /* decorativo */ },
                label = { Text(text = stringResource(id = R.string.app_version)) }
            )
            Spacer(Modifier.height(32.dp))

            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.Email, contentDescription = stringResource(R.string.cd_enviar_email)) },
                text = { Text(stringResource(R.string.cta_contacto_info)) },
                onClick = onEmailClick
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}


// Aceptada lista
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ElemListScreen(
    windowSize: WindowSize,
    animeList: List<Anime>,
    onItemClick: (Anime) -> Unit,
    onFavoriteToggle: (Anime) -> Unit,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier,
    listTitle: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(listTitle) },
                actions = {
                    Button(onClick = onAboutClick) {
                        Text(text = "Acerca de")
                    }
                }
            )
        },
        modifier = modifier
    ) { padding ->

        val columns = when (windowSize) {
            WindowSize.Compact -> 1
            WindowSize.Medium -> 2
            WindowSize.Expanded -> 3
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(animeList) { anime ->
                AnimeItemCard(
                    anime = anime,
                    modifier = Modifier
                        .padding(horizontal = if (columns > 1) 0.dp else 8.dp)
                        .clickable { onItemClick(anime) },
                    onFavoriteClick = { onFavoriteToggle(anime) }
                )
            }
        }
    }
}

//Comentarios
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailItemScreen(
    windowSize: WindowSize,
    anime: Anime,
    onFavoriteToggle: (Anime) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(anime.title, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.cd_volver))
                    }
                }
            )
        },
        modifier = modifier
    ) { padding ->
        val isHorizontalLayout = windowSize != WindowSize.Compact

        if (isHorizontalLayout) {
            Row(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {

                DetailContent(
                    anime = anime,
                    onFavoriteToggle = onFavoriteToggle,
                    modifier = Modifier.weight(1f)
                )


                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Comentarios (${anime.comments.size})",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    anime.comments.ifEmpty { listOf("Sin comentarios aún.") }.forEach { item ->
                        if (item is Comment) {
                            ListItem(
                                headlineContent = { Text(item.user) },
                                supportingContent = { Text(item.text) }
                            )
                        } else if (item is String) {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        } else {

            Column(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                DetailContent(anime = anime, onFavoriteToggle = onFavoriteToggle)


                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = "Comentarios (${anime.comments.size})",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    anime.comments.ifEmpty { listOf("Sin comentarios aún.") }.forEach { item ->
                        if (item is Comment) {
                            ListItem(
                                headlineContent = { Text(item.user) },
                                supportingContent = { Text(item.text) }
                            )
                        } else if (item is String) {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Contenido detallado
@Composable
fun DetailContent(
    anime: Anime,
    onFavoriteToggle: (Anime) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = anime.title,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RatingChip(anime.rating)
        }

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Géneros: ${anime.genres.joinToString()}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(8.dp))
        Text(text = anime.description, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onFavoriteToggle(anime) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (anime.isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
        ) {
            Text(if (anime.isFavorite) "Eliminar de Favoritos" else "Añadir a Favoritos")
        }
    }
}


// --- Pantalla de Favorito
@Composable
fun FavListScreen(
    windowSize: WindowSize,
    favoriteAnimeList: List<Anime>,
    onItemClick: (Anime) -> Unit,
    onFavoriteToggle: (Anime) -> Unit,
    onAboutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElemListScreen(
        windowSize = windowSize,
        animeList = favoriteAnimeList, // Pasa la lista de favoritos filtrada
        onItemClick = onItemClick,
        onFavoriteToggle = onFavoriteToggle,
        onAboutClick = onAboutClick,
        modifier = modifier,
        listTitle = "Mis Animes Favoritos" // Título específico
    )
}


// --- Pantalla Usuario
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Perfil de Usuario") })
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Iniciar Sesión en AniTalks",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Email o Usuario") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { /* Lógica de Login */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar Sesión")
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "¿No tienes cuenta? Regístrate",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { /* Navegar a registro */ }
            )
        }
    }
}