package com.example.usolo.features.auth.data.repository

import android.util.Log
import com.example.authclass10.config.RetrofitConfig
import com.example.usolo.features.auth.data.dto.LoginData
import com.example.usolo.features.auth.data.dto.LogoutRequest
import com.example.usolo.features.auth.data.sources.AuthService
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException

class AuthRepository(
    val authService: AuthService = RetrofitConfig.directusRetrofit.create(AuthService::class.java)
) {

    suspend fun login(loginData: LoginData): Result<Unit> {
        return try {
            val response = authService.login(loginData)
            LocalDataSourceProvider.get().save("accesstoken", response.data.access_token)
            Log.e(">>>", response.data.access_token)
            Result.success(Unit)
        } catch (e: HttpException) {
            Result.failure(Exception("Correo o contraseña incorrectos"))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Ocurrió un error inesperado"))
        }
    }

    suspend fun getAccessToken(): String? {
        var token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
        Log.e(">>>", token.toString())
        return token
    }

    suspend fun logout(refreshToken: String? = null): Boolean {
        return try {
            val response = if (refreshToken != null) {
                authService.logout(LogoutRequest(refreshToken))
            } else {
                authService.logout()
            }
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}