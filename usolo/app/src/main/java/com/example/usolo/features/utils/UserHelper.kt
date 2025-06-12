package com.example.usolo.features.utils

import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

object UserHelper {
    fun getCurrentUserProfileId(): Int? {
        return runBlocking {
            LocalDataSourceProvider.get().getProfileId().firstOrNull()?.toIntOrNull()
        }
    }
}