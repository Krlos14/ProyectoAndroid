package com.example.anitalks.data.model


data class Comment(
    val id: Int,
    val user: String,
    val text: String,
    val timestamp: Long
)


data class Anime(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Float,
    val genres: List<String>,
    val imageResId: Int,
    val isFavorite: Boolean = false,
    val comments: List<Comment> = emptyList()
)