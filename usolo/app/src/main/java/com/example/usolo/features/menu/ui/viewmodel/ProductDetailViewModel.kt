package com.example.usolo.features.menu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.menu.data.model.ProductData
import com.example.usolo.features.menu.data.model.ReviewData
import com.example.usolo.features.menu.data.model.StatusData
import com.example.usolo.features.menu.data.repository.ListProductRepository
import com.example.usolo.features.menu.data.repository.ProductDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ProductDetailViewModel : ViewModel() {
    private val repository = ProductDetailRepository()

    private val _product = MutableStateFlow<ProductData?>(null)
    val product: StateFlow<ProductData?> = _product.asStateFlow()

    private val _status = MutableStateFlow<StatusData?>(null)
    val status: StateFlow<StatusData?> = _status.asStateFlow()

    private val _reviews = MutableStateFlow<List<ReviewData>>(emptyList())
    val reviews: StateFlow<List<ReviewData>> = _reviews.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    fun loadItemDetails(itemId: String) {
        viewModelScope.launch {
            _loading.value = true

            // Cargar el producto
            val productResult = repository.getProduct(itemId)
            if (productResult.isSuccess) {
                val product = productResult.getOrNull()
                _product.value = product

                // Ahora con el producto, podemos buscar status y reviews
                product?.let {
                    _status.value = repository.getItemStatus(it.status_id)
                }
            }

            // Reseñas no dependen del producto, solo del ID
            _reviews.value = repository.getItemReviews(itemId)

            _loading.value = false
        }
    }
}

