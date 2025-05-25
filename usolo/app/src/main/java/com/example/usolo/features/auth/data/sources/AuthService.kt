package com.example.usolo.features.auth.data.sources

import com.example.usolo.features.auth.data.dto.LoginData
import com.example.usolo.features.auth.data.dto.LoginResponse
import com.example.usolo.features.auth.data.dto.LogoutRequest
import com.example.usolo.features.rental_registration.data.dto.DirectusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

data class UserProfile(
    val id: Int,
    val address: String?,
    val user_id: String
)

interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body loginData: LoginData): LoginResponse

    @POST("/auth/logout")
    suspend fun logout(@Body body: LogoutRequest): Response<Void>

    @POST("/auth/logout")
    suspend fun logout(): Response<Void>

    @GET("/items/user_profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
        @Query("filter[user_id][_eq]") userId: String
    ): DirectusResponse<List<UserProfile>>

    @GET("/users/me")
    suspend fun getMe(@Header("Authorization") token: String): LoginUserResponse

    data class LoginUserResponse(val data: LoginUserData)
    data class LoginUserData(val id: String)
}