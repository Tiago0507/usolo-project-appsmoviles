
package com.example.usolo.features.rent.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class RentUiState(
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val telefono: String = "",
    val direccion: String = "",
    val metodoEntrega: String = "Recoger en algún lugar",
    val metodoPago: String = "",
    val numeroTarjeta: String = "",
    val nombreTitular: String = "",
    val fechaExpiracion: String = "",
    val cvv: String = "",
    val tiempoCantidad: String = "1",
    val tiempoUnidad: String = "Días"
)

class RentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RentUiState())
    val uiState: StateFlow<RentUiState> = _uiState

    fun onNombreChange(newValue: String) {
        _uiState.value = _uiState.value.copy(nombre = newValue)
    }

    fun onApellidoChange(newValue: String) {
        _uiState.value = _uiState.value.copy(apellido = newValue)
    }

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)
    }

    fun onTelefonoChange(newValue: String) {
        _uiState.value = _uiState.value.copy(telefono = newValue)
    }

    fun onDireccionChange(newValue: String) {
        _uiState.value = _uiState.value.copy(direccion = newValue)
    }

    fun onMetodoEntregaChange(newValue: String) {
        _uiState.value = _uiState.value.copy(metodoEntrega = newValue)
    }

    fun onMetodoPagoChange(newValue: String) {
        _uiState.value = _uiState.value.copy(metodoPago = newValue)
    }

    fun onNumeroTarjetaChange(newValue: String) {
        _uiState.value = _uiState.value.copy(numeroTarjeta = newValue)
    }

    fun onNombreTitularChange(newValue: String) {
        _uiState.value = _uiState.value.copy(nombreTitular = newValue)
    }

    fun onFechaExpiracionChange(newValue: String) {
        _uiState.value = _uiState.value.copy(fechaExpiracion = newValue)
    }

    fun onCvvChange(newValue: String) {
        _uiState.value = _uiState.value.copy(cvv = newValue)
    }

    fun onTiempoCantidadChange(newValue: String) {
        _uiState.value = _uiState.value.copy(tiempoCantidad = newValue)
    }

    fun onTiempoUnidadChange(newValue: String) {
        _uiState.value = _uiState.value.copy(tiempoUnidad = newValue)
    }
}
