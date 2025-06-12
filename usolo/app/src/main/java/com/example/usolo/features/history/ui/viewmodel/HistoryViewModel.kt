package com.example.usolo.features.history.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.menu.data.repository.ListProductRepository
import com.example.usolo.features.menu.data.repository.ListProductRepositoryImpl
import com.example.usolo.features.products.data.dto.ProductData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: ListProductRepository = ListProductRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryState())
    val uiState: StateFlow<HistoryState> = _uiState.asStateFlow()

    init {
        loadUserProducts()
    }

    private fun loadUserProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val profileId = LocalDataSourceProvider.get().getProfileId().firstOrNull()?.toIntOrNull()

                if (profileId != null) {
                    Log.d("HistoryViewModel", "Loading products for profileId: $profileId")

                    val result = repository.getProductsByProfileId(profileId)

                    result.fold(
                        onSuccess = { products ->
                            Log.d("HistoryViewModel", "Successfully loaded ${products.size} products")
                            _uiState.value = _uiState.value.copy(
                                publishedProducts = products,
                                isLoading = false,
                                error = null
                            )
                        },
                        onFailure = { error ->
                            Log.e("HistoryViewModel", "Error loading products: ${error.message}")
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = error.message ?: "Error desconocido al cargar productos"
                            )
                        }
                    )
                } else {
                    Log.e("HistoryViewModel", "ProfileId is null")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "No se pudo identificar el usuario. Por favor, inicia sesión nuevamente."
                    )
                }
            } catch (e: Exception) {
                Log.e("HistoryViewModel", "Exception loading user products", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error inesperado: ${e.message}"
                )
            }
        }
    }

    fun refreshHistory() {
        Log.d("HistoryViewModel", "Refreshing history")
        loadUserProducts()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class HistoryState(
    val publishedProducts: List<ProductData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val hasProducts: Boolean get() = publishedProducts.isNotEmpty()
    val isEmpty: Boolean get() = publishedProducts.isEmpty() && !isLoading && error == null
}