package com.example.anitalks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.anitalks.data.preferences.UserPreferencesDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val preferences: UserPreferencesDataStore
) : ViewModel() {

    val username = preferences.username.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "Usuario"
    )

    val themeMode = preferences.themeMode.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "system"
    )

    fun saveUsername(username: String) {
        viewModelScope.launch {
            preferences.saveUsername(username)
        }
    }

    fun saveThemeMode(themeMode: String) {
        viewModelScope.launch {
            preferences.saveThemeMode(themeMode)
        }
    }
}

class ProfileViewModelFactory(
    private val preferences: UserPreferencesDataStore
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(preferences) as T
    }
}