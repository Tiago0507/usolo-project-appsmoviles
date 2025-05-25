package com.example.usolo.domain.model

data class ReservationStatus(
    val id: Int,
    val name: String,
    val reservations: List<Reservation>
)
