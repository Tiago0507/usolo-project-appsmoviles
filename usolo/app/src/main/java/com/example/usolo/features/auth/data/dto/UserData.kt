package com.example.usolo.features.auth.data.dto

import java.util.UUID

data class UserData(
    val id: UUID,
)

data class UserProfileResponseDTO(
    val id: Int
)

data class DirectusListResponse<T>(
    val data: List<T>
)

data class DirectusResponse<T>(
    val data: T
)