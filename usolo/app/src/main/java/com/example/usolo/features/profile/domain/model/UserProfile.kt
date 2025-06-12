package com.example.usolo.features.profile.domain.model

data class UserProfile(
    val id: Int,
    val firstName: String,
    val email: String,
    val address: String,
    val profilePhoto: String?,
    val publishedItemsCount: Int = 0,
    val completedRentalsCount: Int = 0
)