package com.example.anitalks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitalks.data.local.FavoriteAnimeEntity
import com.example.anitalks.data.repository.AnimeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: AnimeRepository
) : ViewModel() {

    val favorites = repository.getFavorites()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun deleteFavorite(favorite: FavoriteAnimeEntity) {
        viewModelScope.launch {
            repository.deleteFavorite(favorite)
        }
    }
}