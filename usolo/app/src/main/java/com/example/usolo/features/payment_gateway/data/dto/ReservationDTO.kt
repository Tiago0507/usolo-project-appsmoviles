package com.example.usolo.features.payment_gateway.data.dto

data class CreateReservationDTO(
    val start_date: String,
    val completion_date: String,
    val total_price: Double,
    val profile_id: Int,
    val item_id: Int,
    val status_id: Int = 1
)

data class ReservationDTO(
    val id: Int,
    val start_date: String,
    val completion_date: String,
    val total_price: Double,
    val profile_id: Int,
    val item_id: Int,
    val status_id: Int
)