package com.example.usolo.features.menu.data.repository

import android.util.Log
import com.example.authclass10.config.RetrofitConfig
import com.example.usolo.features.menu.data.model.ProductContainer
import com.example.usolo.features.menu.data.model.ProductData
import com.example.usolo.features.menu.data.model.ReviewData
import com.example.usolo.features.menu.data.model.StatusData
import com.example.usolo.features.products.data.sources.ProductApi

class ProductDetailRepository {
    private val apiService = RetrofitConfig.directusRetrofit.create(ProductApi::class.java)


    suspend fun getItemStatus(statusId: String): StatusData? {
        return try {
            val response = apiService.getStatus(statusId)
            if (response.isSuccessful) {
                response.body()?.data
            } else null
        } catch (e: Exception) {
            Log.e("ProductDetailRepo", "Error fetching status", e)
            null
        }
    }

    suspend fun getItemReviews(itemId: String): List<ReviewData> {
        return try {
            val response = apiService.getReviewsByItem(itemId)
            if (response.isSuccessful) {
                response.body()?.data ?: emptyList()
            } else emptyList()
        } catch (e: Exception) {
            Log.e("ProductDetailRepo", "Error fetching reviews", e)
            emptyList()
        }
    }    suspend fun getProduct(itemId: String): Result<ProductData> {
        return try {
            Log.e("ProductDetailRepo", "Intentando obtener producto con ID: $itemId")
            val productResponse = apiService.getProduct(itemId)
            if (!productResponse.isSuccessful) {
                Log.e("ProductDetailRepo", "Error de API al obtener producto: ${productResponse.errorBody()?.string()}")
                return Result.failure(
                    Exception(
                        "Error al obtener producto: ${
                            productResponse.errorBody()?.string()
                        }"
                    )
                )
            }

            val productContainer = productResponse.body() ?: return Result.failure(
                Exception("Respuesta del producto vacía")
            )

            // Accedemos a .data que contiene el producto individual
            val product = productContainer.data
            Log.e("ProductDetailRepo", "Producto obtenido con éxito: $product")

            Result.success(product)
        } catch (e: Exception) {
            Log.e("ProductDetailRepo", "Excepción al obtener producto: ${e.message}", e)
            Result.failure(e)
        }
    }
}
