package com.example.anitalks.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_settings")

class UserPreferencesDataStore(private val context: Context) {

    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val THEME_KEY = stringPreferencesKey("theme_mode")
    }

    val username = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY] ?: "Usuario"
    }

    val themeMode = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: "system"
    }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    suspend fun saveThemeMode(themeMode: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = themeMode
        }
    }
}