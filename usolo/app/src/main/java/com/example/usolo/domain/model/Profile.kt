package com.example.usolo.domain.model

data class Profile(
    val id: Int,
    val name: String,
    val lastname: String,
    val password: String,
    val address: String,
    val profilePhotoUrl: String,
    val items: List<Item>,
    val reservations: List<Reservation>,
)
