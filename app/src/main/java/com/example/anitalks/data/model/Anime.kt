package com.example.anitalks.data.model

data class Anime(
    val id: Int,
    val title: String,
    val descriptionResId: Int, // Usamos ID de recurso para traducir
    val imageResId: Int,
    val rating: Float,
    var isFavorite: Boolean = false,
    val comments: List<Comment> = emptyList()
)

data class Comment(
    val user: String,
    val text: String
)

