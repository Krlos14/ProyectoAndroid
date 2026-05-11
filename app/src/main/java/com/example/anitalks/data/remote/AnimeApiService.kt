package com.example.anitalks.data.remote

import retrofit2.http.GET

data class AnimeResponse(
    val data: List<AnimeDto>
)

data class AnimeDto(
    val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val status: String?,
    val year: Int?,
    val score: Float?,
    val images: AnimeImages
)

data class AnimeImages(
    val jpg: AnimeImageUrl
)

data class AnimeImageUrl(
    val image_url: String
)

interface AnimeApiService {

    @GET("anime")
    suspend fun getAnimeList(): AnimeResponse
}