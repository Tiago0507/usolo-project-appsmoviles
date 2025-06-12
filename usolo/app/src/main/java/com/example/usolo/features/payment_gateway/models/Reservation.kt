package com.example.usolo.features.payment_gateway.models

data class Reservation(
    val id: Int,
    val startDate: String,
    val completionDate: String,
    val totalPrice: Double,
    val profileId: Int,
    val itemId: Int,
    val statusId: Int
)