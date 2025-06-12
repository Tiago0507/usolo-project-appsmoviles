package com.example.usolo.features.products.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.menu.data.repository.ListProductRepository
import com.example.usolo.features.menu.data.repository.ListProductRepositoryImpl
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ReviewData
import com.example.usolo.features.products.data.repository.ProductDetailRepository
import com.example.usolo.features.products.data.repository.impl.ProductDetailRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.Instant

class ProductDetailViewModel(
    private val productId: Int,
    private val productDetailRepository: ProductDetailRepository = ProductDetailRepositoryImpl()
) : ViewModel() {

    private val _product = MutableStateFlow<ProductData?>(null)
    val product: StateFlow<ProductData?> = _product

    private val _reviews = mutableStateListOf<ReviewData>()
    val reviews: List<ReviewData> get() = _reviews

    private val _userNames = mutableStateListOf<String>()
    val userNames: List<String> get() = _userNames

    private val _isCreatingReview = MutableStateFlow(false)
    val isCreatingReview: StateFlow<Boolean> = _isCreatingReview

    private val localDataSource = LocalDataSourceProvider.get()

    init {
        fetchProductDetails()
        fetchProductReviews()
    }

    private fun fetchProductDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val token = localDataSource.load("accesstoken").firstOrNull()
            if (token != null && token.isNotEmpty()) {
                try {
                    val productData = productDetailRepository.getProductDetails(productId, token)
                    _product.value = productData
                } catch (e: Exception) {
                    Log.e("ProductDetailViewModel", "Error al cargar producto: ${e.message}")
                }
            }
        }
    }

    private fun fetchProductReviews() {
        viewModelScope.launch(Dispatchers.IO) {
            val token = localDataSource.load("accesstoken").firstOrNull()
            if (token != null && token.isNotEmpty()) {
                try {
                    Log.e("<<<<<<<<<<<<<<<<<<<<", "Entreeeeeeeeeeeeeeeeeeeeeeeee")
                    Log.e("ProductDetailViewModel", "IDDDDDDDDD: $productId")
                    val reviewsList = productDetailRepository.getProductReviews(productId, token) ?: emptyList()
                    _reviews.clear()
                    _reviews.addAll(reviewsList)
                    Log.e("ProductDetailViewModel", "RESULTADOOOOOOOOOOO: $reviewsList")

                    // Obtener nombres de usuarios de las reviews
                    val userNamesList = productDetailRepository.getUsersFromReviews(reviewsList, token) ?: emptyList()
                    _userNames.clear()
                    _userNames.addAll(userNamesList)

                } catch (e: Exception) {
                    Log.e("ProductDetailViewModel", "Error al cargar reviews: ${e.message}")
                }
            }
        }
    }
    fun createReview(rating: Float, comment: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isCreatingReview.value = true

            try {
                val token = localDataSource.load("accesstoken").firstOrNull()
                val profileId = localDataSource.getProfileId().firstOrNull()?.toIntOrNull()

                if (token != null && profileId != null && comment.isNotBlank()) {
                    val timestamp = Instant.now().toString()
                    Log.e("ViewModellllllllll", "$timestamp")
                    val result = productDetailRepository.createReview(
                        rating = rating,
                        comment = comment,
                        publicationDate = timestamp,
                        itemId = productId,
                        profileId = profileId,
                        token = token
                    )

                    result.onSuccess {
                        // Recargar las reviews después de crear una nueva
                        fetchProductReviews()
                        Log.d("ProductDetailViewModel", "Review creada exitosamente")
                    }.onFailure { exception ->
                        Log.e("ProductDetailViewModel", "Error creando review: ${exception.message}")
                    }
                } else {
                    Log.e("ProductDetailViewModel", "Datos insuficientes para crear review")
                }
            } catch (e: Exception) {
                Log.e("ProductDetailViewModel", "Error en createReview: ${e.message}")
            } finally {
                _isCreatingReview.value = false
            }
        }
    }
}
