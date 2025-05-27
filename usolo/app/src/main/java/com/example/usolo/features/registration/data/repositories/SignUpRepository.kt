package com.example.usolo.features.registration.data.repositories

import com.example.usolo.features.registration.data.dto.SignUpRequestDTO
import com.example.usolo.features.registration.data.dto.UserWithProfileDTO


interface SignUpRepository {
    suspend fun signUp(signUpRequest: SignUpRequestDTO): Result<UserWithProfileDTO>
}
