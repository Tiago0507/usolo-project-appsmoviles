package com.example.usolo.features.rental_registration.domain.model

data class RentalItem(
    val id: String,
    val title: String,
    val imageUrl: String,
    val pricePerDay: String,
    val availability: Boolean
)