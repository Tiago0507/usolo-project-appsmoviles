package com.example.usolo.domain.model

data class Reservation(
    val id: Int,
    val startDate: String,
    val completionDate: String,
    val totalPrice: Double,
    val reservationStatus: ReservationStatus
)
