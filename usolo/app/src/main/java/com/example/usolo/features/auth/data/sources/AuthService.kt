package com.example.usolo.features.auth.data.sources

import com.example.usolo.features.auth.data.dto.LoginData
import com.example.usolo.features.auth.data.dto.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body loginData: LoginData): LoginResponse
}