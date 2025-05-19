package com.example.usolo.features.landlord.data.dto

data class ItemDTO(
    val title: String,
    val pricePerDay: String?,
    val imageRes: Int?,
    val description: String? = null,
    val location: String? = null,
    val availability: Boolean? = null,
    val photo: String? = null
)
