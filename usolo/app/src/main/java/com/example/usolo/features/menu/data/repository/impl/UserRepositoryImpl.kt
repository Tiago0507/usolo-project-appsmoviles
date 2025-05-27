package com.example.usolo.features.menu.data.repository

import android.util.Log
import com.example.usolo.config.RetrofitConfig
import com.example.usolo.features.auth.data.sources.AuthService
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import kotlinx.coroutines.flow.firstOrNull

class UserRepositoryImpl(
    private val authService: AuthService = RetrofitConfig.directusRetrofit.create(AuthService::class.java)
): UserRepository {

    override suspend fun getUserNameByProfileId(profileId: Int): Result<String> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
                ?: return Result.failure(Exception("Token no disponible"))

            val response = authService.getUserProfileWithUserName(
                token = "Bearer $token",
                profileId = profileId
            )

            val name = response.data.firstOrNull()?.user_id?.first_name ?: "Desconocido"
            Result.success(name)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error obteniendo nombre: ${e.message}")
            Result.failure(e)
        }
    }
}
