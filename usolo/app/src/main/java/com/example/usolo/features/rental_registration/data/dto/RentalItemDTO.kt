package com.example.usolo.features.rental_registration.data.dto


import com.squareup.moshi.Json

data class RentalItemDTO(
    val id: Int,
    val title: String,
    val description: String?,
    @Json(name = "price_per_day") val pricePerDay: String,
    val location: String?,
    val availability: Boolean,
    val photo: String,
    @Json(name = "profile_id") val profileId: Int,
    @Json(name = "category_id") val categoryId: Int,
    @Json(name = "status_id") val statusId: Int
)

