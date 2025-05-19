package com.example.usolo.features.products.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.menu.data.repository.ListProductRepository
import com.example.usolo.features.products.data.dto.ProductData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productsList: ListProductRepository = ListProductRepository()
) : ViewModel() {

    private val _products = mutableStateListOf<ProductData>()
    val products: List<ProductData> get() = _products

    init {
        showProducts()
    }

    private fun showProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = productsList.getProductsByProfileId(1)
            Log.e("Products: ", result.toString())


            result.onSuccess { productDataList ->
                val uiProducts = productDataList.map { productData ->
                    ProductData(
                        title = productData.title ?: "",
                        description = productData.description ?: "",
                        price_per_day = productData.price_per_day ?: 0.0,
                        category_id = productData.category_id ?: 0,
                        status_id = productData.status_id ?: 0,
                        photo = productData.photo ?: "",
                        profile_id = productData.profile_id?:0,
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
