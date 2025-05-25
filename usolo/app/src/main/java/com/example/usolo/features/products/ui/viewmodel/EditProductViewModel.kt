package com.example.usolo.features.products.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.menu.data.repository.ListProductRepository
import com.example.usolo.features.products.data.dto.Category
import com.example.usolo.features.products.data.dto.ItemStatus
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import com.example.usolo.features.products.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditProductViewModel(
    private val repository: ProductRepository = ProductRepository(),
    private val listProductRepository: ListProductRepository = ListProductRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditProductState>(EditProductState.Idle)
    val uiState = _uiState.asStateFlow()
    private val _statuses = MutableStateFlow<List<ItemStatus>>(emptyList())
    val statuses: StateFlow<List<ItemStatus>> = _statuses
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    val selectedProduct = MutableStateFlow<ProductData?>(null)

    fun loadProductById(id: Int){
        viewModelScope.launch {
            val product = repository.getProduct(id) // suspend fun{
            Log.e("ayudaaaa", product.id.toString())
            selectedProduct.value = product
        }
    }

    fun updateProduct(itemId: Int, product: ProductUpdateDto) {
        _uiState.value = EditProductState.Loading
        viewModelScope.launch {
            val result = repository.updateProduct(itemId, product)
            _uiState.value = result.fold(
                onSuccess = { EditProductState.Success(it) },
                onFailure = { EditProductState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    fun deleteProduct(itemId: Int) {
        viewModelScope.launch {
            repository.deleteProduct(itemId)
            _uiState.value = EditProductState.Deleted
        }
    }

    fun loadStatuses() {
        viewModelScope.launch {
            try {
                val statusList = repository.getItemStatuses()
                Log.e("list",statusList.toString())
                _statuses.value = statusList
            } catch (e: Exception) {
                Log.e("Error chistoso",e.toString())
            }
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val categoryList = repository.getCategories()
                _categories.value = categoryList
            } catch (e: Exception) {
                Log.e("Error chistoso",e.toString())
            }
        }
    }

    fun getStatusNameById(id: Int): String {
        return statuses.value.find { it.id == id }?.name ?: ""
    }

    fun getStatusIdByName(name: String): Int {
        return statuses.value.find { it.name == name }?.id ?: 0
    }

    fun getCategoryIdByName(name: String): Int {
        return categories.value.find { it.name == name }?.id ?: 0
    }


    sealed class EditProductState {
        object Idle : EditProductState()
        object Loading : EditProductState()
        data class Success(val product: ProductData) : EditProductState()
        data class Error(val message: String) : EditProductState()
        object Deleted : EditProductState()
    }
}