package com.projectAnya.stunthink.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = context.dataStore
    }

    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    suspend fun deleteUserToken() {
        dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN_KEY)
        }
    }

    val userTokenFlow: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY]
        }

    companion object {
        val USER_TOKEN_KEY = stringPreferencesKey("user_token")
    }
}

