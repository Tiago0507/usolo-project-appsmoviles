package com.example.usolo.features.auth.data.sources

import com.example.usolo.features.auth.data.dto.*
import retrofit2.Response
import retrofit2.http.*

interface AuthService {

    @POST("/auth/login")
    suspend fun login(@Body loginData: LoginData): LoginResponse

    @POST("/auth/logout")
    suspend fun logout(@Body body: LogoutRequest): Response<Void>

    @POST("/auth/logout")
    suspend fun logout(): Response<Void>

    @GET("/users/me")
    suspend fun getMe(
        @Header("Authorization") token: String
    ): DirectusResponse<UserData>

    @GET("/items/user_profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,
        @Query("filter[user_id][_eq]") userId: String
    ): DirectusResponse<List<UserProfileResponseDTO>>
}
