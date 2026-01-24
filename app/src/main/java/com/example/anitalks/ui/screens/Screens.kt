package com.example.anitalks.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.anitalks.R
import com.example.anitalks.data.model.Anime
import com.example.anitalks.ui.components.AnimeItemCard
import com.example.anitalks.ui.util.WindowSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElemListScreen(
    windowSize: WindowSize,
    animeList: List<Anime>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onItemClick: (Anime) -> Unit,
    onFavoriteClick: (Anime) -> Unit,
    onAboutClick: () -> Unit, // Añadido para que coincida con la llamada
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(stringResource(R.string.title_anime_list)) },
                    actions = { IconButton(onClick = onAboutClick) { Icon(Icons.Default.Info, null) } }
                )
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = { Text(stringResource(R.string.search_placeholder)) },
                    leadingIcon = { Icon(Icons.Default.Search, null) }
                )
            }
        },
        modifier = modifier
    ) { p ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (windowSize == WindowSize.Compact) 1 else 2),
            contentPadding = p
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailItemScreen(
    windowSize: WindowSize,
    anime: Anime,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(anime.title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { p ->
        Column(
            modifier = Modifier
                .padding(p)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {

            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = anime.imageResId),
                contentDescription = anime.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Ajusta la altura a tu gusto
                    .padding(bottom = 16.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop // Para que rellene el espacio
            )

            // Puntuación
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(4.dp))
                Text("Rating: ${anime.rating}/5", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(Modifier.height(16.dp))

            //Descripcion
            Text(
                text = stringResource(R.string.title_description),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(anime.descriptionResId),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(24.dp))

            // Comentarios
            Text(
                text = stringResource(R.string.title_comments),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            anime.comments.forEach { comment ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Text(comment.user, fontWeight = FontWeight.Bold)
                        Text(comment.text)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(isLoggedIn: Boolean, onLoginToggle: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {

        Text(if (isLoggedIn) stringResource(R.string.profile_welcome) else stringResource(R.string.profile_not_logged))
        Spacer(Modifier.height(16.dp))
        Button(onClick = onLoginToggle) {
            Text(stringResource(if (isLoggedIn) R.string.btn_logout else R.string.btn_login))
        }
    }
}

@Composable
fun AboutScreen(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {

        Text(stringResource(R.string.about_version))
        Spacer(Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text(stringResource(R.string.btn_back))
        }
    }
}