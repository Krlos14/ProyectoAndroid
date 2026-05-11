package com.example.anitalks.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_anime")
data class FavoriteAnimeEntity(

    @PrimaryKey
    val id: Int,

    val title: String,
    val imageUrl: String
)