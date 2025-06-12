package com.example.usolo.features.payment_gateway.data.dto

data class PaymentMethodResponse(
    val data: List<PaymentMethodDTO>
)

data class PaymentMethodDTO(
    val id: Int,
    val name: String,
    val description: String
)