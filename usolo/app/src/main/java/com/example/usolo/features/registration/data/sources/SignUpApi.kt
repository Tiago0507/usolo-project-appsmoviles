package com.example.usolo.features.registration.data.sources

import com.example.usolo.features.registration.data.dto.DirectusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import com.example.usolo.features.registration.data.dto.UserResponseDTO
import com.example.usolo.features.registration.data.dto.ProfileCreateDTO
import com.example.usolo.features.registration.data.dto.ProfileResponseDTO
import com.example.usolo.features.registration.data.dto.UserCreateDTO
import com.example.usolo.features.registration.data.dto.SignUpRequestDTO
import com.example.usolo.features.registration.data.dto.UserWithProfileDTO




interface SignUpApi{
    @POST("users")
    suspend fun createUser(
        @Body user: UserCreateDTO): Response<DirectusResponse<UserResponseDTO>>

    @POST("items/user_profile")
    suspend fun createProfile(
        @Body profile: ProfileCreateDTO): Response<DirectusResponse<ProfileResponseDTO>>


    @GET("users/me")
    suspend fun getCurrentUser(
        @Header("Authorization") token: String
    ): Response<UserResponseDTO>

    @GET("items/user_profile")
    suspend fun getUserProfile(
        @Query("filter[user_id][_eq]") userId: String,
        @Header("Authorization") token: String
    ): Response<Map<String, List<ProfileResponseDTO>>>
}