package com.example.usolo.features.menu.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usolo.R
import com.example.usolo.features.menu.data.model.Product
import com.example.usolo.features.menu.data.repository.ListProductRepository
import com.example.usolo.features.products.data.dto.ProductData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(
    val productsList: ListProductRepository = ListProductRepository()
) : ViewModel() {

    private val _products = mutableStateListOf<ProductData>()
    val products: List<ProductData> get() = _products

    init {
        showProducts()
    }

    private fun loadMockProducts() {
        _products.addAll(
            listOf(

            )
        )
    }

    private fun showProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = productsList.getProducts()
            Log.e("Products: ", result.toString())


            result.onSuccess { productDataList ->
                val uiProducts = productDataList.map { productData ->
                    ProductData(
                        title = productData.title ?: "",
                        description = productData.description ?: "",
                        pricePerDay = productData.pricePerDay ?: 0.0,
                        category = productData.category ?: "",
                        status = productData.status ?: "",
                        photoUrl = productData.photoUrl ?: "",
                        availability = productData.availability ?: true
                    )
                }

                _products.clear()
                _products.addAll(uiProducts)
            }

            result.onFailure {
                // Manejar error (podrías emitir un evento o cambiar un estado de error)
            }
        }
    }
}
