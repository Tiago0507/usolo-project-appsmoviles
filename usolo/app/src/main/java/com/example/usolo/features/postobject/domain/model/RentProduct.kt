package com.example.usolo.features.postobject.domain.model

data class RentProduct(
    val title: String,
    val description: String,
    val price_per_day: Double,
    val category_id: Int,
    val availability: Boolean = true,
    val photo: String? = null,
    val profile_id: Int,
    val status_id: Int = 1
)
