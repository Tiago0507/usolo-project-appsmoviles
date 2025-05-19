package com.example.usolo.features.rent.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class NuevaPantallaUiState(
    val nombre: String = "",
    val apellido: String = "",
    val email: String = "",
    val telefono: String = "",
    val direccion: String = "",
    val metodoEntrega: String = "Recoger",
    val metodoPago: String = "",
    val numeroTarjeta: String = "",
    val nombreTitular: String = "",
    val fechaExpiracion: String = "",
    val cvv: String = ""
)


class NuevaPantallaViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(NuevaPantallaUiState())
    val uiState: StateFlow<NuevaPantallaUiState> = _uiState

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

    fun onNombreTitularChange(newValue: String){


    }    }
