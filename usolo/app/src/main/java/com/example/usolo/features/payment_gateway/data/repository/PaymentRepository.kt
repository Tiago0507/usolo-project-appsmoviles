package com.example.usolo.features.payment_gateway.data.repository

import com.example.usolo.features.payment_gateway.models.Payment
import com.example.usolo.features.payment_gateway.models.PaymentMethod
import com.example.usolo.features.payment_gateway.models.Reservation

interface PaymentRepository {
    suspend fun getPaymentMethods(): Result<List<PaymentMethod>>
    suspend fun createReservation(
        startDate: String,
        endDate: String,
        totalPrice: Double,
        profileId: Int,
        itemId: Int
    ): Result<Reservation>
    suspend fun processPayment(
        amount: Double,
        reservationId: Int,
        methodId: Int,
        paymentDate: String
    ): Result<Payment>
}