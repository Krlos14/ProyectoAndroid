package com.example.anitalks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.anitalks.R
import com.example.anitalks.data.model.Anime
import com.example.anitalks.ui.theme.AnimeTalksFavoriteIconDark
import com.example.anitalks.ui.theme.AnimeTalksFavoriteIconLight
import com.example.anitalks.ui.theme.AnimeTalksRatingStarDark
import com.example.anitalks.ui.theme.AnimeTalksRatingStarLight
import androidx.compose.ui.layout.ContentScale

// --- A. Tarjeta de Elementos (AnimeItemCard) ---
@Composable
fun AnimeItemCard(
    anime: Anime,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit = {}
) {

    val favColor = if (anime.isFavorite) {
        if (isSystemInDarkTheme()) AnimeTalksFavoriteIconDark else AnimeTalksFavoriteIconLight
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen (Corregida en el intento anterior, ahora solo la mantenemos)
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            ) {
                Image(
                    painter = painterResource(anime.imageResId),
                    contentDescription = anime.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = anime.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )
                RatingChip(rating = anime.rating)
            }


            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (anime.isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = stringResource(R.string.cd_favorito),
                    tint = favColor
                )
            }
        }
    }
}



@Composable
fun RatingChip(rating: Float, modifier: Modifier = Modifier) {

    val chipColor = when (rating) {
        in 4.0f..5.0f -> MaterialTheme.colorScheme.secondary
        in 3.0f..3.9f -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }


    val starColor = if (isSystemInDarkTheme()) AnimeTalksRatingStarDark else AnimeTalksRatingStarLight

    AssistChip(
        modifier = modifier.height(32.dp),
        onClick = { /* No interactivo */ },
        label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = starColor
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = rating.toString(),
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        },

        colors = AssistChipDefaults.assistChipColors(
            containerColor = chipColor,
            labelColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}