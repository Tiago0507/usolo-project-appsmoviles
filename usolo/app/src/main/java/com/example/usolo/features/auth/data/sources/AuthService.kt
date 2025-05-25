package com.example.usolo.features.auth.data.sources

import com.example.usolo.features.auth.data.dto.DirectusListResponse
import com.example.usolo.features.auth.data.dto.DirectusResponse
import com.example.usolo.features.auth.data.dto.LoginData
import com.example.usolo.features.auth.data.dto.LoginResponse
import com.example.usolo.features.auth.data.dto.LogoutRequest
import com.example.usolo.features.auth.data.dto.UserData
import com.example.usolo.features.auth.data.dto.UserProfileResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.UUID

interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body loginData: LoginData): LoginResponse

    @POST("/auth/logout")
    suspend fun logout(@Body body: LogoutRequest): Response<Void>

    @POST("/auth/logout")
    suspend fun logout(): Response<Void>

    @GET("users/me")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ): DirectusResponse<UserData>

    @GET("items/user_profile")
    suspend fun getCurrentUserProfile(
        @Header("Authorization") token: String,
        @Query("filter[user_id][_eq]") userId: UUID
    ) : DirectusListResponse<UserProfileResponseDTO>
}