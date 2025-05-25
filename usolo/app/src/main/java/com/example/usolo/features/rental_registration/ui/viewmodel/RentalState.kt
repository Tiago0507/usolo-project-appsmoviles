package com.example.usolo.features.rental_registration.ui.viewmodel

import com.example.usolo.features.rental_registration.domain.model.RentalItem

data class RentalState(
    val publishedItems: List<RentalItem> = emptyList(),
    val rentedItems: List<RentalItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)