package com.trackfox.app.service

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("user_session_prefs")
class UserSessionManager(private val context : Context) {
    private val prefs : SharedPreferences = context.getSharedPreferences("user_session_prefs",
        Context.MODE_PRIVATE)

    companion object {
        private val USER_ID = longPreferencesKey("current_user_id")
        private val USER_NAME = stringPreferencesKey("current_user_name")
        private val TOKEN = stringPreferencesKey("current_user_auth_token")
    }

    private val currentUserIdFlow : Flow<Long?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }
        .map { prefs ->
            prefs[USER_ID]
        }

    private val currentUserNameFlow : Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }
        .map { prefs ->
            prefs[USER_NAME]
        }

    private val currentAuthTokenFlow : Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }
        .map { prefs ->
            prefs[TOKEN]
        }

    suspend fun getCurrentUserId() : Long = currentUserIdFlow.first() ?: -1L

    suspend fun getCurrentUserName() : String = currentUserNameFlow.first() ?: ""

    suspend fun getCurrentUserToken() : String = currentAuthTokenFlow.first() ?: ""

    suspend fun setUser(id : Long, name : String, token : String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = id
        }

        context.dataStore.edit { prefs ->
            prefs[USER_NAME] = name
        }

        context.dataStore.edit { prefs ->
            prefs[TOKEN] = token
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID)
            prefs.remove(USER_NAME)
            prefs.remove(TOKEN)
        }
    }
}