package com.example.usolo.features.registration.data.dto

data class DirectusResponse<T>(
    val data: T
)

data class UserCreateDTO(
    val first_name: String,
    val email: String,
    val password: String,
    val role: String = "11111111-1111-1111-1111-111111111111" // ID del rol "Usuario"
)

// DTO para la respuesta de creación de usuario
data class UserResponseDTO(
    val id: String,
    val first_name: String,
    val email: String,
    val role: String
)