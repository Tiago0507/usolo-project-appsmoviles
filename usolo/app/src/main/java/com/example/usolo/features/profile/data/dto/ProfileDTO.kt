package com.example.usolo.features.profile.data.dto

data class UserProfileDTO(
    val id: Int,
    val address: String?,
    val profile_photo: String?,
    val user_id: String
)

data class UserDirectusDTO(
    val id: String,
    val first_name: String?,
    val email: String?
)