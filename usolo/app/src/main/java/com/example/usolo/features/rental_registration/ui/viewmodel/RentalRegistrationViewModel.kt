package com.example.usolo.features.rental_registration.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.usolo.features.rental_registration.data.dto.RentalItemDTO

class RentalRegistrationViewModel(
    private val repository: RentalRegistrationRepository = RentalRegistrationRepository()
): ViewModel() {
    private val _publishedProducts = mutableStateListOf<RentalItemDTO>()
    val publishedProducts: List<RentalItemDTO> get() = _publishedProducts

    private val _rentedProducts = mutableStateListOf<RentalItemDTO>()
    val rentedProducts: List<RentalItemDTO> get() = _rentedProducts

    init {
        loadMockData()
    }

    private fun loadMockData() {
        _publishedProducts.addAll(repository.getPublishedProducts())
        _rentedProducts.addAll(repository.getRentedProducts())
    }

}