package com.medical.ecommercetask.util.dataStore

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userData")

class DataStoreUtil(context: Context) {

    private val dataStore = context.dataStore
    private val locality = stringPreferencesKey("locality")
    private val nightMode = intPreferencesKey("nightMode")
    private val state = booleanPreferencesKey("state")
    private val userId = intPreferencesKey("userId")
    private val userType = stringPreferencesKey("userType")
    private val userEmail = stringPreferencesKey("userEmail")
    private val userMobile = stringPreferencesKey("userMobile")
    private val userName = stringPreferencesKey("userName")
    private val token = stringPreferencesKey("token")

    suspend fun setLocality(language: String) {
        dataStore.edit { userData ->
            userData[locality] = language
        }
    }

    val localityFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[locality] ?: "ar"
    }

    suspend fun setNightMode(mode: Int) {
        dataStore.edit { userData ->
            userData[nightMode] = mode
        }
    }

    val nightModeFlow: Flow<Int> = dataStore.data.map { preferences ->
        preferences[nightMode] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    suspend fun setState(state_b: Boolean) {
        dataStore.edit { userData ->
            userData[state] = state_b
        }
    }

    val stateFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[state] ?: false
    }

    suspend fun setUserId(id: Int) {
        dataStore.edit { userData ->
            userData[userId] = id
        }
    }

    val userIdFlow: Flow<Int> = dataStore.data.map { preferences ->
        preferences[userId] ?: 0
    }

    suspend fun setUserMobile(mobile: String) {
        dataStore.edit { userData ->
            userData[userMobile] = mobile
        }
    }

    val userMobileFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[userMobile] ?: ""
    }

    suspend fun setUserName(_userName: String) {
        dataStore.edit { userData ->
            userData[userName] = _userName
        }
    }

    val userNameFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[userName] ?: ""
    }

    suspend fun setUserType(userType_: String) {
        dataStore.edit { userData ->
            userData[userType] = userType_
        }
    }

    val userTypeFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[userType] ?: ""
    }

    suspend fun setUserEmail(email_: String) {
        dataStore.edit { userData ->
            userData[userEmail] = email_
        }
    }

    val userEmailFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[userEmail] ?: ""
    }

    suspend fun setToken(accessToken: String) {
        dataStore.edit { userData ->
            userData[token] = accessToken
        }
    }

    val tokenFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[token] ?: ""
    }

}