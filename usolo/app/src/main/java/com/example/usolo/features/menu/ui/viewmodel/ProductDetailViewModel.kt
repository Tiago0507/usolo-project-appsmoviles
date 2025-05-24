package com.example.usolo.features.menu.ui.viewmodel

import android.util.Log
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
        Log.d("ProductDetailVM", "Iniciando loadItemDetails con ID: $itemId")
        viewModelScope.launch {
            _loading.value = true

            // Cargar el producto
            val productResult = repository.getProduct(itemId)
            if (productResult.isSuccess) {
                val product = productResult.getOrNull()
                Log.d("ProductDetailVM", "Producto obtenido con éxito: $product")
                _product.value = product
                    Log.d("ProductDetailVM", "Cargando status para el producto")
                if (product != null) {
                    _status.value = repository.getItemStatus(product.status_id)
                }

            } else {
                Log.e("ProductDetailVM", "Error al obtener producto: ${productResult.exceptionOrNull()?.message}")
            }

            // Reseñas no dependen del producto, solo del ID
            _reviews.value = repository.getItemReviews(itemId)

            _loading.value = false
            Log.d("ProductDetailVM", "Finalizada carga de detalles, producto: ${_product.value}")
        }
    }
}

