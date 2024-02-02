package com.ecommerce.task.util.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userData")

class DataStoreUtil(context: Context) {

    private val dataStore = context.dataStore
    private val locality = stringPreferencesKey("locality")
    suspend fun setLocality(language: String) {
        dataStore.edit { userData ->
            userData[locality] = language
        }
    }

    val localityFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[locality] ?: "en"
    }

}