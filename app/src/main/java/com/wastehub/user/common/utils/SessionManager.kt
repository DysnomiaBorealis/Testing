package com.wastehub.user.common.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@ViewModelScoped
class SessionManager @Inject constructor(private val sessionDataStore: DataStore<Preferences>) {

    fun getAccessToken(): Flow<String?> {
        return sessionDataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return sessionDataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }
    }

    suspend fun saveToken(accessToken: String?, refreshToken: String?) {
        sessionDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken ?: ""
            preferences[REFRESH_TOKEN_KEY] = refreshToken ?: ""
        }
    }

    suspend fun deleteToken() {
        sessionDataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }

    suspend fun saveSession(session: Boolean) {
        sessionDataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = session
        }
    }

    val isUserLoggedIn: Flow<Boolean?> = sessionDataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN_KEY]
    }

    val userId: Flow<String?> = sessionDataStore.data.map { preferences ->
        preferences[USER_ID_KEY]
    }


    companion object {
        val USER_ID_KEY = stringPreferencesKey("userId")
        val ACCESS_TOKEN_KEY = stringPreferencesKey("accessToken")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refreshToken")
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }
}