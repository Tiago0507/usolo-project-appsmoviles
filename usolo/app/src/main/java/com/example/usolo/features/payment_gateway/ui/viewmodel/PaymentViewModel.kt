package com.example.usolo.features.payment_gateway.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.payment_gateway.data.repository.PaymentRepository
import com.example.usolo.features.payment_gateway.data.repository.impl.PaymentRepositoryImpl
import com.example.usolo.features.payment_gateway.models.PaymentMethod
import com.example.usolo.features.products.data.dto.ProductData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PaymentViewModel(
    private val repository: PaymentRepository = PaymentRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState

    private val _paymentMethods = MutableStateFlow<List<PaymentMethod>>(emptyList())
    val paymentMethods: StateFlow<List<PaymentMethod>> = _paymentMethods

    init {
        loadPaymentMethods()
    }

    private fun loadPaymentMethods() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            repository.getPaymentMethods().fold(
                onSuccess = { methods ->
                    _paymentMethods.value = methods
                    _uiState.value = _uiState.value.copy(isLoading = false)
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }

    fun processReservationAndPayment(
        product: ProductData,
        startDate: String,
        endDate: String,
        selectedPaymentMethod: PaymentMethod
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val profileId = LocalDataSourceProvider.get().getProfileId().firstOrNull()?.toIntOrNull()
            if (profileId == null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error: No se encontró el perfil del usuario"
                )
                return@launch
            }

            // Calcular precio total
            val days = calculateDaysBetween(startDate, endDate)
            val totalPrice = product.price_per_day * days

            // Crear reserva
            repository.createReservation(
                startDate = startDate,
                endDate = endDate,
                totalPrice = totalPrice,
                profileId = profileId,
                itemId = product.id
            ).fold(
                onSuccess = { reservation ->
                    // Procesar pago
                    repository.processPayment(
                        amount = totalPrice,
                        reservationId = reservation.id,
                        methodId = selectedPaymentMethod.id,
                        paymentDate = getCurrentDate()
                    ).fold(
                        onSuccess = {
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                paymentSuccess = true,
                                successMessage = "¡Pago procesado exitosamente!"
                            )
                        },
                        onFailure = { error ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = "Error al procesar pago: ${error.message}"
                            )
                        }
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Error al crear reserva: ${error.message}"
                    )
                }
            )
        }
    }

    private fun calculateDaysBetween(startDate: String, endDate: String): Long {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val start = format.parse(startDate)
        val end = format.parse(endDate)
        return ((end!!.time - start!!.time) / (1000 * 60 * 60 * 24)) + 1
    }

    private fun getCurrentDate(): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(Date())
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun resetPaymentState() {
        _uiState.value = PaymentUiState()
    }
}

data class PaymentUiState(
    val isLoading: Boolean = false,
    val paymentSuccess: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)