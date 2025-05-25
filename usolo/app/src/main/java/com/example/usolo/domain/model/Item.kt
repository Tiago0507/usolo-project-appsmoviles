package com.example.usolo.domain.model

data class Item(
    val id: Int,
    val title: String,
    val description: String,
    val pricePerDay: Double,
    val location: String,
    val availability: Boolean,
    val photoUrl: String,
    val categories: List<Category>,
    val status: ItemStatus,
    val owner: Profile
)
