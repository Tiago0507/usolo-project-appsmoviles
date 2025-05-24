package com.example.usolo.features.menu.data.model

data class User(
    val name: String,
    val title: String,
    val profileImageRes: Int
)
// DTOs para User Profile
data class UserProfileData(
    val id: Int,
    val address: String?,
    val profile_photo: String?,
    val user_id: String
)

data class UserProfileContainer(
    val data: UserProfileData
)

data class UserResponseDTO(
    val id: String,
    val first_name: String,
    val email: String,
    val role: String
)
data class DirectusResponse<T>(
    val data: T
)