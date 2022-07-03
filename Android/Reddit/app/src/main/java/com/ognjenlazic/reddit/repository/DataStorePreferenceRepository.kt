package com.ognjenlazic.reddit.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.core.remove
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorePreferenceRepository(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "UserInfo")

    private var token = ""

    companion object {
        val PREF_KEY = preferencesKey<String>("user")

        private var INSTANCE: DataStorePreferenceRepository? = null

        fun getInstance(context: Context): DataStorePreferenceRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = DataStorePreferenceRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

    //setValue
    suspend fun setUserInformation(token: String) {
        dataStore.edit { preferences ->
            preferences[PREF_KEY] = token
        }
    }

    //getValue
    val getUserInformation: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PREF_KEY] ?: token
        }

    suspend fun removeUser() {
        dataStore.edit { preferences ->
            preferences.remove(PREF_KEY)
        }
    }
}














