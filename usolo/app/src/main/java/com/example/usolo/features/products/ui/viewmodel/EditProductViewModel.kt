package com.example.usolo.features.products.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.usolo.features.products.data.repository.ProductRepository

class EditProductViewModel : ViewModel() {

    val productRepository = ProductRepository()

    var name by mutableStateOf("")
    var price by mutableStateOf("")
    var category by mutableStateOf("")
    var state by mutableStateOf("En buen estado")

    fun onNameChange(newName: String) {
        name = newName
    }

    fun onPriceChange(newPrice: String) {
        price = newPrice
    }

    fun onCategoryChange(newCategory: String) {
        category = newCategory
    }

    fun onStateChange(newState: String) {
        state = newState
    }

    fun saveProductChanges(productId: String) {


    }
}
