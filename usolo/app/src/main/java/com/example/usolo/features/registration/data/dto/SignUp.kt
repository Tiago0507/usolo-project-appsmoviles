package com.example.usolo.features.registration.data.dto


// DTO para el registro completo (usuario + perfil)
data class SignUpRequestDTO(
    val first_name: String,
    val email: String,
    val password: String,
    val address: String,
    val profile_photo: String? = null
)

// DTO para la respuesta completa (uniendo usuario y perfil)
data class UserWithProfileDTO(
    val id: String,
    val first_name: String,
    val email: String,
    val address: String,
    val profile_photo: String?
)