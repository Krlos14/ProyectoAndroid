package com.example.anitalks.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Query("SELECT * FROM favorite_anime")
    fun getFavorites(): Flow<List<FavoriteAnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(anime: FavoriteAnimeEntity)

    @Delete
    suspend fun deleteFavorite(anime: FavoriteAnimeEntity)

    @Query("SELECT * FROM comments WHERE animeId = :animeId")
    fun getComments(animeId: Int): Flow<List<CommentEntity>>

    @Insert
    suspend fun insertComment(comment: CommentEntity)
}