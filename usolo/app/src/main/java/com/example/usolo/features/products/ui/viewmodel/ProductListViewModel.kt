package com.example.usolo.features.products.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.menu.data.repository.ListProductRepository
import com.example.usolo.features.menu.data.repository.ListProductRepositoryImpl
import com.example.usolo.features.products.data.dto.ProductData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productsList: ListProductRepository = ListProductRepositoryImpl()
) : ViewModel() {

    private val _products = mutableStateListOf<ProductData>()
    val products: List<ProductData> get() = _products

    init {
        showProducts()
    }

    private fun showProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val userProfileId = LocalDataSourceProvider.get().load("user_profile").firstOrNull()
            if (userProfileId == null) {
                Log.e("ProductListViewModel", "No user_profile found in local data store")
                return@launch
            }
            val userId = userProfileId.toInt()
            val result = productsList.getProductsByProfileId(userId)


            result.onSuccess { productDataList ->
                val uiProducts = productDataList.map { productData ->
                    ProductData(
                        id = productData.id,
                        title = productData.title ?: "",
                        description = productData.description ?: "",
                        price_per_day = productData.price_per_day ?: 0.0,
                        category_id = productData.category_id ?: 0,
                        status_id = productData.status_id ?: 1,
                        photo = productData.photo ?: "",
                        profile_id = productData.profile_id?:0,
                        availability = productData.availability ?: true
                    )
                }

                _products.clear()
                _products.addAll(uiProducts)
            }

            result.onFailure {
                Log.e("ProductListViewModel", "Error fetching products: ${it.message}")
            }
        }
    }
}
