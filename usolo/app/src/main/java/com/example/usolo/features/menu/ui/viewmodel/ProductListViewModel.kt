package com.example.usolo.features.menu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.example.usolo.R
import com.example.usolo.features.menu.data.model.Product

class ProductListViewModel : ViewModel() {

    private val _products = mutableStateListOf<Product>()
    val products: List<Product> get() = _products

    init {
        //mock
        loadMockProducts()
    }

    private fun loadMockProducts() {
        _products.addAll(
            listOf(
                Product("Silla Gamer LED", "$10.000 por día", R.drawable.silla_gamer),
                Product("Cámara Sony A7", "$60.000 por día", R.drawable.camara_sony)
            )
        )
    }
}
