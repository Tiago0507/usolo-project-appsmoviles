package com.example.usolo.features.auth.data.repository
import com.example.usolo.features.auth.data.dto.LoginData

interface AuthRepository {
    suspend fun login(loginData: LoginData): Result<Unit>
    suspend fun getAccessToken(): String?
    suspend fun logout(refreshToken: String? = null): Boolean
    suspend fun getUserNameByProfileId(profileId: Int): String?
    suspend fun debugAuthData()
    suspend fun getProfileId(): String?

}
