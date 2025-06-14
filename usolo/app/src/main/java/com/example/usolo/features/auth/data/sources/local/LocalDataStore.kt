package com.example.usolo.features.auth.data.sources.local

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

object LocalDataSourceProvider{
    private var instance: LocalDataStore? = null
    fun init(dataStore: DataStore<Preferences>) {
        if (instance == null) {
            instance = LocalDataStore(dataStore)
        }
    }

    fun get(): LocalDataStore{
        return instance ?: throw IllegalStateException("LocalDataStore no está inicializado")
    }
}


class LocalDataStore(val dataStore: DataStore<Preferences>) {
    suspend fun save(key: String, value: String) {
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey(key)] = value
        }
    }

    fun load(key: String): Flow<String> = dataStore.data.map { prefs ->
        prefs[stringPreferencesKey(key)] ?: ""
    }

    suspend fun saveProfileId(key: String, profileId: String) {
        save(key, profileId)
    }

    fun getProfileId(): Flow<String> {
        return load("profile_id")
    }

    suspend fun clearUserData() {
        dataStore.edit { prefs ->
            prefs.remove(stringPreferencesKey("profile_id"))
            prefs.remove(stringPreferencesKey("directus_user_id"))
            prefs.remove(stringPreferencesKey("accesstoken"))
        }
    }

    fun loadTokenBlocking(): String {
        return runBlocking {
            load("accesstoken").firstOrNull() ?: ""
        }
    }
}