package com.example.usolo.features.rental_registration.data.dto

data class RentalItemDTO(
    val id: String,
    val title: String,
    val imageUrl: String,
    val pricePerDay: String,
    val availability: Boolean
)
