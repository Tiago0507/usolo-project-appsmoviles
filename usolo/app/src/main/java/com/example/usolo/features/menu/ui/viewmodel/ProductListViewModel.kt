package com.example.usolo.features.menu.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ActivityScenario.launch
import com.example.usolo.R
import com.example.usolo.features.menu.data.model.Product
import com.example.usolo.features.menu.data.model.ProductData
import com.example.usolo.features.menu.data.model.ProductWithUser
import com.example.usolo.features.menu.data.model.UserProfileData
import com.example.usolo.features.menu.data.repository.ListProductRepository
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {

    private val repository = ListProductRepository()

    private val _products = mutableStateListOf<ProductWithUser>()
    val products: List<ProductWithUser> get() = _products

    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    private val _error = mutableStateOf<String?>(null)
    val error: String? get() = _error.value


    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            repository.getProducts()
                .onSuccess { productDataList ->
                    _products.clear()
                    _products.addAll(productDataList)

                }
                .onFailure { exception ->
                    _error.value = exception.message

                }

            _isLoading.value = false
        }
    }

    fun retryLoading() {
        loadProducts()
    }


/*
    private fun loadMockProducts() {
        // Mantenemos como ProductData para consistencia
        _products.clear()
        _products.addAll(
            listOf(
                ProductData(
                    title = "Silla Gamer LED",
                    description = "Silla para gaming con luces LED",
                    pricePerDay = 10000,
                    category = "Gaming",
                    status = "available",
                    photoUrl = "", // URL vacía para usar placeholder
                    availability = true
                ),
                ProductData(
                    title = "Cámara Sony A7",
                    description = "Cámara profesional Sony",
                    pricePerDay = 60000,
                    category = "Photography",
                    status = "available",
                    photoUrl = "",
                    availability = true
                )
            )
        )
    }
    */

}
