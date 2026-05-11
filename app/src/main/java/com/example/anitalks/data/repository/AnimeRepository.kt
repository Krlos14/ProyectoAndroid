package com.example.anitalks.data.repository

import com.example.anitalks.data.local.AnimeDao
import com.example.anitalks.data.local.CommentEntity
import com.example.anitalks.data.local.FavoriteAnimeEntity
import com.example.anitalks.data.model.Anime
import com.example.anitalks.data.remote.RetrofitClient

class AnimeRepository(
    private val dao: AnimeDao
) {

    suspend fun getAnimeList(): List<Anime> {

        val response = RetrofitClient.api.getAnimeList()

        return response.data.map {

            Anime(
                id = it.mal_id,
                title = it.title,
                synopsis = it.synopsis ?: "Sin descripción",
                imageUrl = it.images.jpg.image_url,
                score = it.score ?: 0f,
                episodes = it.episodes,
                status = it.status,
                year = it.year
            )
        }
    }

    fun getFavorites() = dao.getFavorites()

    suspend fun insertFavorite(anime: FavoriteAnimeEntity) {
        dao.insertFavorite(anime)
    }

    suspend fun deleteFavorite(anime: FavoriteAnimeEntity) {
        dao.deleteFavorite(anime)
    }

    fun getComments(animeId: Int) = dao.getComments(animeId)

    suspend fun insertComment(comment: CommentEntity) {
        dao.insertComment(comment)
    }
}