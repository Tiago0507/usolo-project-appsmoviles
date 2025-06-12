package com.example.usolo.features.payment_gateway.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.menu.data.repository.ListProductRepository
import com.example.usolo.features.menu.data.repository.ListProductRepositoryImpl
import com.example.usolo.features.products.data.dto.ProductData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentNavigationViewModel(
    private val repository: ListProductRepository = ListProductRepositoryImpl()
) : ViewModel() {

    private val _product = MutableStateFlow<ProductData?>(null)
    val product: StateFlow<ProductData?> = _product

    fun loadProduct(productId: Int) {
        viewModelScope.launch {
            repository.getProduct(productId).fold(
                onSuccess = { productData ->
                    _product.value = productData
                },
                onFailure = {
                    _product.value = null
                }
            )
        }
    }
}