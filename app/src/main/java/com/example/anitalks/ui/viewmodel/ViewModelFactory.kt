package com.example.anitalks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.anitalks.data.repository.AnimeRepository

class AnimeListViewModelFactory(
    private val repository: AnimeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimeListViewModel(repository) as T
    }
}

class FavoriteViewModelFactory(
    private val repository: AnimeRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repository) as T
    }
}

class DetailViewModelFactory(
    private val repository: AnimeRepository,
    private val animeId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repository, animeId) as T
    }
}