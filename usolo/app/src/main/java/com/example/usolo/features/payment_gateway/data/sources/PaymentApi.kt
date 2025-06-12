package com.example.usolo.features.payment_gateway.data.sources

import com.example.usolo.features.payment_gateway.data.dto.CreatePaymentDTO
import com.example.usolo.features.payment_gateway.data.dto.CreateReservationDTO
import com.example.usolo.features.payment_gateway.data.dto.PaymentDTO
import com.example.usolo.features.payment_gateway.data.dto.PaymentMethodResponse
import com.example.usolo.features.payment_gateway.data.dto.ReservationDTO
import retrofit2.Response
import retrofit2.http.*


import com.example.usolo.features.registration.data.dto.DirectusResponse

interface PaymentApi {

    @GET("items/payment_method")
    suspend fun getPaymentMethods(
        @Header("Authorization") token: String
    ): PaymentMethodResponse

    @POST("items/reservation")
    suspend fun createReservation(
        @Body reservation: CreateReservationDTO,
        @Header("Authorization") token: String
    ): Response<DirectusResponse<ReservationDTO>>

    @POST("items/payment")
    suspend fun createPayment(
        @Body payment: CreatePaymentDTO,
        @Header("Authorization") token: String
    ): Response<DirectusResponse<PaymentDTO>>
}