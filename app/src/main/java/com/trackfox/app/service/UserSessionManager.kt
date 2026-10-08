package com.trackfox.app.service

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.trackfox.app.server.service.RequestService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("user_session_prefs")
class UserSessionManager @Inject constructor(
    @ApplicationContext private val context : Context,
    private val requestService: RequestService) {

    private val prefs : SharedPreferences = context.getSharedPreferences("user_session_prefs",
        Context.MODE_PRIVATE)

    companion object {
        private val USER_ID = longPreferencesKey("current_user_id")
        private val USER_NAME = stringPreferencesKey("current_user_name")
        private val ACCESS_TOKEN = stringPreferencesKey("current_user_access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("current_user_refresh_token")
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

    private val currentAccessTokenFlow : Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }
        .map { prefs ->
            prefs[ACCESS_TOKEN]
        }

    private val currentRefreshTokenFlow : Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }
        .map { prefs ->
            prefs[ACCESS_TOKEN]
        }

    suspend fun getCurrentUserId() : Long = currentUserIdFlow.first() ?: -1L

    suspend fun getCurrentUserName() : String = currentUserNameFlow.first() ?: ""

    suspend fun getCurrentUserAccessToken() : String = currentAccessTokenFlow.first() ?: ""

    suspend fun getCurrentUserRefreshToken() : String = currentRefreshTokenFlow.first() ?: ""

    suspend fun setUser(id : Long, name : String, accessToken : String, refreshToken : String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = id
            prefs[USER_NAME] = name
            prefs[ACCESS_TOKEN] = accessToken
            prefs[REFRESH_TOKEN] = refreshToken
        }
    }

    @Synchronized
    fun refreshTokens() : String {
        val currentRefreshToken = runBlocking {
            context.dataStore.data.first()[REFRESH_TOKEN]
        } ?: return ""

        return try {
            val response = runBlocking { requestService.refreshTokens(currentRefreshToken) }

            if (response.isSuccessful && response.body() != null) {
                val refreshToken = response.body()?.refreshToken ?: return ""
                val accessToken = response.body()?.accessToken ?: return ""
                runBlocking {
                    context.dataStore.edit { prefs ->
                        prefs[ACCESS_TOKEN] = accessToken
                        prefs[REFRESH_TOKEN] = refreshToken
                    }
                }
                accessToken
            } else {
                runBlocking {
                    clearUserData()
                }
                ""
            }
        } catch (_ : Exception) {
            ""
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID)
            prefs.remove(USER_NAME)
            prefs.remove(ACCESS_TOKEN)
            prefs.remove(key = REFRESH_TOKEN)
        }
    }
}