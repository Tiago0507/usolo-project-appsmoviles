package com.example.usolo.features.payment_gateway.data.dto

data class CreatePaymentDTO(
    val amount: Double,
    val payment_date: String,
    val reservation_id: Int,
    val status_id: Int = 2, // Pagado
    val method_id: Int
)

data class PaymentDTO(
    val id: Int,
    val amount: Double,
    val payment_date: String,
    val reservation_id: Int,
    val status_id: Int,
    val method_id: Int
)