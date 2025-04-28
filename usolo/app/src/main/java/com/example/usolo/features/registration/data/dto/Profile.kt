package com.example.usolo.features.registration.data.dto

// DTO para crear un perfil de usuario
data class ProfileCreateDTO(
    val address: String,
    val profile_photo: String?,
    val user_id: String
)

// DTO para la respuesta de perfil
data class ProfileResponseDTO(
    val id: Int,
    val address: String,
    val profile_photo: String?,
    val user_id: String
)
