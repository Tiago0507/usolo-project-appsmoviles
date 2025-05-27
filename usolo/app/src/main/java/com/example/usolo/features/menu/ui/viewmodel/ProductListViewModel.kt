package com.example.usolo.features.menu.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.menu.data.repository.ListProductRepository
import com.example.usolo.features.menu.data.repository.UserRepository
import com.example.usolo.features.products.data.dto.ProductData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull


class ProductListViewModel(
    private val productsList: ListProductRepository = ListProductRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _products = mutableStateListOf<ProductData>()
    val products: List<ProductData> get() = _products

    private val _userNames = mutableStateMapOf<Int, String>() // profileId -> userName
    val userNames: Map<Int, String> get() = _userNames

    init {
        showProducts()
    }

    private fun showProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            // Obtener tu propio profile ID
            val userProfileId = LocalDataSourceProvider.get().load("user_profile").firstOrNull()?.toIntOrNull()

            if (userProfileId == null) {
                Log.e("ProductListViewModel", "No se encontró un profile_id válido")
                return@launch
            }

            val result = productsList.getProducts()

            result.onSuccess { productDataList ->
                val filteredProducts = productDataList.filter { it.profile_id != userProfileId }

                _products.clear()
                _products.addAll(filteredProducts)

                // Obtener nombres de usuario para los productos restantes
                filteredProducts.forEach { product ->
                    val profileId = product.profile_id
                    if (profileId != 0 && !_userNames.containsKey(profileId)) {
                        val nameResult = userRepository.getUserNameByProfileId(profileId)
                        nameResult.onSuccess { name ->
                            _userNames[profileId] = name
                        }.onFailure {
                            Log.e("UserRepo", "Error cargando nombre para profileId $profileId: ${it.message}")
                        }
                    }
                }
            }

            result.onFailure {
                Log.e("ProductListViewModel", "Error al cargar productos: ${it.message}")
            }
        }
    }
}
