package com.example.anitalks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitalks.data.local.FavoriteAnimeEntity
import com.example.anitalks.data.model.Anime
import com.example.anitalks.data.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AnimeListViewModel(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun loadAnimeList() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val favorites = repository.getFavorites().first()
                val favoriteIds = favorites.map { it.id }

                val apiAnime = repository.getAnimeList()

                _animeList.value = apiAnime.map { anime ->
                    anime.copy(isFavorite = favoriteIds.contains(anime.id))
                }

            } catch (e: Exception) {
                _message.value = "Error al cargar los animes desde la API"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveFavorite(anime: Anime) {
        viewModelScope.launch {
            val favorites = repository.getFavorites().first()
            val alreadySaved = favorites.any { it.id == anime.id }

            if (alreadySaved) {
                _message.value = "Este anime ya está guardado como favorito"
            } else {
                repository.insertFavorite(
                    FavoriteAnimeEntity(
                        id = anime.id,
                        title = anime.title,
                        imageUrl = anime.imageUrl
                    )
                )

                _animeList.value = _animeList.value.map {
                    if (it.id == anime.id) it.copy(isFavorite = true) else it
                }

                _message.value = "Anime guardado como favorito"
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}