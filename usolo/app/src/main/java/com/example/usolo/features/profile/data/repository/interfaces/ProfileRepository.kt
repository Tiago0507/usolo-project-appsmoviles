package com.example.usolo.features.profile.data.repository.interfaces

import com.example.usolo.features.profile.domain.model.UserProfile

interface ProfileRepository {
    suspend fun getUserProfile(profileId: Int, directusUserId: String): Result<UserProfile>
}