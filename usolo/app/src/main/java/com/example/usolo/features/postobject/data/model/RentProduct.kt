package com.example.usolo.features.postobject.data.model

data class RentProduct(
    val title: String,
    val description: String,
    val price_per_day: Double,
    val category: String,
    val availability: Boolean = true,
    val photo: String? = null
)
