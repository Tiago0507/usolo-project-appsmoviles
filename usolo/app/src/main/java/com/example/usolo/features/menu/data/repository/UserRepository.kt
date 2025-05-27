package com.example.usolo.features.menu.data.repository

interface UserRepository {
    suspend fun getUserNameByProfileId(profileId: Int): Result<String>
}
