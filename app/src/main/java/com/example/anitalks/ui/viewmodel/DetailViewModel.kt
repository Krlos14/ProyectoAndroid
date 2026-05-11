package com.example.anitalks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anitalks.data.local.CommentEntity
import com.example.anitalks.data.repository.AnimeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: AnimeRepository,
    animeId: Int
) : ViewModel() {

    val comments = repository.getComments(animeId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addComment(animeId: Int, user: String, text: String) {
        viewModelScope.launch {
            repository.insertComment(
                CommentEntity(
                    animeId = animeId,
                    user = user,
                    text = text
                )
            )
        }
    }
}