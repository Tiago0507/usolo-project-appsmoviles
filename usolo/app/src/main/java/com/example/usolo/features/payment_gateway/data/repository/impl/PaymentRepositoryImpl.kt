package com.example.usolo.features.payment_gateway.data.repository.impl

import com.example.usolo.config.RetrofitConfig
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.payment_gateway.data.dto.CreatePaymentDTO
import com.example.usolo.features.payment_gateway.data.dto.CreateReservationDTO
import com.example.usolo.features.payment_gateway.data.repository.PaymentRepository
import com.example.usolo.features.payment_gateway.data.sources.PaymentApi
import com.example.usolo.features.payment_gateway.models.Payment
import com.example.usolo.features.payment_gateway.models.PaymentMethod
import com.example.usolo.features.payment_gateway.models.Reservation
import kotlinx.coroutines.flow.firstOrNull

class PaymentRepositoryImpl(
    private val paymentApi: PaymentApi = RetrofitConfig.directusRetrofit.create(PaymentApi::class.java)
) : PaymentRepository {

    override suspend fun getPaymentMethods(): Result<List<PaymentMethod>> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
            val response = paymentApi.getPaymentMethods("Bearer $token")

            val paymentMethods = response.data.map { dto ->
                PaymentMethod(
                    id = dto.id,
                    name = dto.name,
                    description = dto.description
                )
            }
            Result.success(paymentMethods)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createReservation(
        startDate: String,
        endDate: String,
        totalPrice: Double,
        profileId: Int,
        itemId: Int
    ): Result<Reservation> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
            val createDto = CreateReservationDTO(
                start_date = startDate,
                completion_date = endDate,
                total_price = totalPrice,
                profile_id = profileId,
                item_id = itemId,
                status_id = 1
            )

            val response = paymentApi.createReservation(createDto, "Bearer $token")

            if (response.isSuccessful) {
                val reservationDto = response.body()?.data
                    ?: return Result.failure(Exception("Respuesta vacía"))

                val reservation = Reservation(
                    id = reservationDto.id,
                    startDate = reservationDto.start_date,
                    completionDate = reservationDto.completion_date,
                    totalPrice = reservationDto.total_price,
                    profileId = reservationDto.profile_id,
                    itemId = reservationDto.item_id, // sin error
                    statusId = reservationDto.status_id
                )
                Result.success(reservation)
            } else {
                Result.failure(Exception("Error al crear reserva: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun processPayment(
        amount: Double,
        reservationId: Int,
        methodId: Int,
        paymentDate: String
    ): Result<Payment> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
            val createDto = CreatePaymentDTO(
                amount = amount,
                payment_date = paymentDate,
                reservation_id = reservationId,
                status_id = 2, // Pagado
                method_id = methodId
            )

            val response = paymentApi.createPayment(createDto, "Bearer $token")

            if (response.isSuccessful) {
                val paymentDto = response.body()?.data
                    ?: return Result.failure(Exception("Respuesta vacía"))

                val payment = Payment(
                    id = paymentDto.id,
                    amount = paymentDto.amount,
                    paymentDate = paymentDto.payment_date,
                    reservationId = paymentDto.reservation_id,
                    statusId = paymentDto.status_id,
                    methodId = paymentDto.method_id
                )
                Result.success(payment)
            } else {
                Result.failure(Exception("Error al procesar pago: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}