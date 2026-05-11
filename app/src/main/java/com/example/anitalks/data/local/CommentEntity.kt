package com.example.anitalks.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val animeId: Int,
    val user: String,
    val text: String
)