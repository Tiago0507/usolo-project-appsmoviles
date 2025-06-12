// File: usolo/app/src/main/java/com/example/usolo/features/profile/data/repository/impl/ProfileRepositoryImpl.kt
package com.example.usolo.features.profile.data.repository.impl

import android.util.Log
import com.example.usolo.config.RetrofitConfig
import com.example.usolo.features.auth.data.sources.AuthService
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.profile.domain.model.UserProfile
import com.example.usolo.features.products.data.sources.ProductApi
import com.example.usolo.features.profile.data.repository.interfaces.ProfileRepository
import kotlinx.coroutines.flow.firstOrNull

class ProfileRepositoryImpl(
    private val authService: AuthService = RetrofitConfig.directusRetrofit.create(AuthService::class.java),
    private val productApi: ProductApi = RetrofitConfig.directusRetrofit.create(ProductApi::class.java)
) : ProfileRepository {

    override suspend fun getUserProfile(profileId: Int, directusUserId: String): Result<UserProfile> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
                ?: return Result.failure(Exception("Token no disponible"))

            Log.d("ProfileRepository", "Getting profile for profileId: $profileId, userId: $directusUserId")

            // Obtener información del usuario actual (me)
            val userResponse = authService.getMe("Bearer $token")
            Log.d("ProfileRepository", "User response: $userResponse")

            // Obtener estadísticas de productos publicados
            val publishedItemsResponse = productApi.getProductsByProfileId(profileId, "Bearer $token")
            val publishedItemsCount = publishedItemsResponse.data.size

            Log.d("ProfileRepository", "Published items count: $publishedItemsCount")

            // Crear el perfil de usuario con datos disponibles
            val userProfile = UserProfile(
                id = profileId,
                firstName = "Usuario USOLO #$profileId",
                email = "usuario@usolo.com",
                address = "Dirección no especificada",
                profilePhoto = null, // Por ahora null hasta resolver la estructura
                publishedItemsCount = publishedItemsCount,
                completedRentalsCount = 0
            )

            Log.d("ProfileRepository", "UserProfile created successfully: $userProfile")
            Result.success(userProfile)

        } catch (e: Exception) {
            Log.e("ProfileRepository", "Error getting user profile", e)
            // Crear un perfil básico como fallback
            val fallbackProfile = UserProfile(
                id = profileId,
                firstName = "Usuario USOLO",
                email = "usuario@usolo.com",
                address = "Dirección no disponible",
                profilePhoto = null,
                publishedItemsCount = 0,
                completedRentalsCount = 0
            )
            Result.success(fallbackProfile)
        }
    }
}