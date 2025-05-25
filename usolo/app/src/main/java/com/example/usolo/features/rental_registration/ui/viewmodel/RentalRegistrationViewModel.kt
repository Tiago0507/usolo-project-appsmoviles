package com.example.usolo.features.rental_registration.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.rental_registration.data.repository.RentalRegistrationRepository
import com.example.usolo.features.rental_registration.data.repository.impl.RentalRegistrationRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RentalRegistrationViewModel(
    private val repository: RentalRegistrationRepository = RentalRegistrationRepositoryImpl()
): ViewModel() {

    private val _uiState = MutableStateFlow(RentalState())
    val uiState: StateFlow<RentalState> = _uiState.asStateFlow()

    fun loadItems(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val publishedResult = repository.getMyPublishedItems(userId)
                val rentedResult = repository.getMyRentedItems(userId)

                _uiState.value = _uiState.value.copy(
                    publishedItems = publishedResult.getOrElse { emptyList() },
                    rentedItems = rentedResult.getOrElse { emptyList() },
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al cargar los datos",
                    isLoading = false
                )
            }
        }
    }
}