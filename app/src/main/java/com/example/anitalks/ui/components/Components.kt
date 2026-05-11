package com.example.anitalks.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.anitalks.data.model.Anime
import com.example.anitalks.ui.theme.AnimeTalksFavoriteIconDark
import com.example.anitalks.ui.theme.AnimeTalksFavoriteIconLight
import com.example.anitalks.ui.theme.AnimeTalksRatingStarDark
import com.example.anitalks.ui.theme.AnimeTalksRatingStarLight

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
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = anime.imageUrl,
                contentDescription = anime.title,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .fillMaxSize()
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = anime.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1
                )

                RatingChip(score = anime.score)
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (anime.isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = null,
                    tint = favColor
                )
            }
        }
    }
}

@Composable
fun RatingChip(
    score: Float,
    modifier: Modifier = Modifier
) {
    val chipColor = when {
        score >= 8f -> MaterialTheme.colorScheme.secondary
        score >= 6f -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }

    val starColor = if (isSystemInDarkTheme()) {
        AnimeTalksRatingStarDark
    } else {
        AnimeTalksRatingStarLight
    }

    AssistChip(
        modifier = modifier.height(32.dp),
        onClick = {},
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = starColor
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = score.toString(),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
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