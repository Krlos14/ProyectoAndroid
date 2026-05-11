package com.example.anitalks.data.model

data class Anime(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val synopsis: String,
    val score: Float,
    val episodes: Int?,
    val status: String?,
    val year: Int?,
    val isFavorite: Boolean = false
)

data class Comment(
    val id: Int = 0,
    val animeId: Int,
    val user: String,
    val text: String
)