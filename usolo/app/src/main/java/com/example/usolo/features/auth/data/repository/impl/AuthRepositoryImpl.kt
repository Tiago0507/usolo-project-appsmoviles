package com.example.usolo.features.auth.data.repository

import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.usolo.config.RetrofitConfig
import com.example.usolo.features.auth.data.dto.LoginData
import com.example.usolo.features.auth.data.dto.LogoutRequest
import com.example.usolo.features.auth.data.sources.AuthService
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val authService: AuthService = RetrofitConfig.directusRetrofit.create(AuthService::class.java)
): AuthRepository {

    override suspend fun login(loginData: LoginData): Result<Unit> {
        return try {
            val response = authService.login(loginData)
            val accessToken = response.data.access_token
            LocalDataSourceProvider.get().save("accesstoken", accessToken)

            // Obtener ID de usuario autenticado
            val userResponse = authService.getMe("Bearer $accessToken")
            val directusUserId = userResponse.data.id.toString() // Convertimos UUID a String
            LocalDataSourceProvider.get().save("directus_user_id", directusUserId)

            // Obtener profileId
            val profileId = getProfileId(directusUserId, accessToken)
            if (profileId != null) {
                LocalDataSourceProvider.get().saveProfileId("profile_id", profileId)
                LocalDataSourceProvider.get().save("user_profile", profileId)

                Log.d("AUTH_REPO", "Login exitoso - ProfileId: $profileId")
            } else {
                Log.w("AUTH_REPO", "No se encontró profile para el usuario: $directusUserId")
            }

            Result.success(Unit)
        } catch (e: HttpException) {
            Log.e("AUTH_REPO", "Error HTTP en login: ${e.code()}", e)
            Result.failure(Exception("Correo o contraseña incorrectos"))
        } catch (e: Exception) {
            Log.e("AUTH_REPO", "Error inesperado en login", e)
            Result.failure(Exception("Ocurrió un error inesperado"))
        }
    }


    private suspend fun getProfileId(directusUserId: String, token: String): String? {
        return try {
            val response = authService.getUserProfile("Bearer $token", directusUserId)
            response.data.firstOrNull()?.id?.toString()
        } catch (e: Exception) {
            Log.e("AUTH_REPO", "Error obteniendo profile_id", e)
            null
        }
    }

    override suspend fun getAccessToken(): String? {
        val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
        Log.d("AUTH_REPO", "Token obtenido: ${token?.take(10) ?: "null"}...")
        return token
    }

     override suspend fun logout(refreshToken: String?): Boolean {
        return try {
            val response = if (refreshToken != null) {
                authService.logout(LogoutRequest(refreshToken))
            } else {
                authService.logout()
            }

            LocalDataSourceProvider.get().clearUserData()
            LocalDataSourceProvider.get().dataStore.edit { prefs ->
                prefs.remove(stringPreferencesKey("accesstoken"))
                prefs.remove(stringPreferencesKey("directus_user_id"))
            }
            Firebase.messaging.unsubscribeFromTopic(
                LocalDataSourceProvider.get().load("user_profile").firstOrNull()
                .toString())
            Log.d("AUTH_REPO", "Logout exitoso")
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("AUTH_REPO", "Error en logout", e)
            false
        }
    }

    override suspend fun getUserNameByProfileId(profileId: Int): String? {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").first()
            val response = authService.getUserProfileWithUserName("Bearer $token", profileId)
            response.data.firstOrNull()?.user_id?.first_name
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getProfileId(): String? {
        return LocalDataSourceProvider.get().getProfileId().firstOrNull()
    }


    override suspend fun debugAuthData() {
        try {
            val profileId = LocalDataSourceProvider.get().getProfileId().firstOrNull()
            val directusUserId = LocalDataSourceProvider.get().load("directus_user_id").firstOrNull()
            val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()

            Log.d("DEBUG_AUTH", "╔═══════════════════════════════")
            Log.d("DEBUG_AUTH", "║ Current ProfileId: ${profileId ?: "null"}")
            Log.d("DEBUG_AUTH", "║ Current DirectusUserId: ${directusUserId ?: "null"}")
            Log.d("DEBUG_AUTH", "║ Current Token: ${token?.take(10) ?: "null"}...")
            Log.d("DEBUG_AUTH", "╚═══════════════════════════════")
        } catch (e: Exception) {
            Log.e("DEBUG_AUTH", "Error al leer datos", e)
        }
    }
}
