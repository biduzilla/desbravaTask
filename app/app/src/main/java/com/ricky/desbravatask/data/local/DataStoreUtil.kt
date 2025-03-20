package com.ricky.desbravatask.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.ricky.desbravatask.domain.models.Login
import com.ricky.desbravatask.domain.models.Token
import com.ricky.desbravatask.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreUtil(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.SETTINGS)

        val THEME_KEY = booleanPreferencesKey(Constants.IS_DARK_MODE)
        val TOKEN = stringPreferencesKey(Constants.USER_TOKEN)
        val LOGIN = stringPreferencesKey(Constants.USER_LOGIN)
    }

    suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDark
        }
    }

    suspend fun saveToken(token: Token) {
        val json = Gson().toJson(token)
        context.dataStore.edit { p -> p[TOKEN] = json }
    }

    suspend fun saveLogin(login: Login) {
        val json = Gson().toJson(login)
        Log.i("infoteste", "saveLogin: $json")
        context.dataStore.edit { preferences ->
            preferences[LOGIN] = json
        }
    }

    fun getTheme(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    fun getToken(): Flow<Token?> {
        return context.dataStore.data.map { preferences ->
            val json = preferences[TOKEN] ?: ""
            if (json.isNotEmpty()) {
                Gson().fromJson(json, Token::class.java)
            } else {
                null
            }
        }
    }

    fun getLogin(): Flow<Login?> {
        return context.dataStore.data.map { preferences ->
            val json = preferences[LOGIN] ?: ""
            if (json.isNotEmpty()) {
                Gson().fromJson(json, Login::class.java)
            } else {
                null
            }
        }
    }
}