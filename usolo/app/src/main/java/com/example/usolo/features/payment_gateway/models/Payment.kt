package com.example.usolo.features.payment_gateway.models

data class Payment(
    val id: Int,
    val amount: Double,
    val paymentDate: String,
    val reservationId: Int,
    val statusId: Int,
    val methodId: Int
)