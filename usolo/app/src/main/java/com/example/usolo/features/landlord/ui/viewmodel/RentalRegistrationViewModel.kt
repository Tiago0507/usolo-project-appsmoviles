package com.example.usolo.features.landlord.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.usolo.features.landlord.data.dto.ItemDTO
import com.example.usolo.features.landlord.data.repository.RentalRegistrationRepository

class RentalRegistrationViewModel(
    private val repository: RentalRegistrationRepository = RentalRegistrationRepository()
): ViewModel() {
    private val _publishedProducts = mutableStateListOf<ItemDTO>()
    val publishedProducts: List<ItemDTO> get() = _publishedProducts

    private val _rentedProducts = mutableStateListOf<ItemDTO>()
    val rentedProducts: List<ItemDTO> get() = _rentedProducts

    init {
        loadMockData()
    }

    private fun loadMockData() {
        _publishedProducts.addAll(repository.getPublishedProducts())
        _rentedProducts.addAll(repository.getRentedProducts())
    }

}