package com.example.usolo.features.menu.data.repository

import android.util.Log
import com.example.authclass10.config.RetrofitConfig
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.sources.ProductApi

class ListProductRepository {

    private val apiService = RetrofitConfig.directusRetrofit.create(ProductApi::class.java)

    suspend fun getProducts(): Result<List<ProductData>> {
        return try {
            val productsResponse = apiService.getProducts()
            if (!productsResponse.isSuccessful) {
                return Result.failure(
                    Exception("Error al obtener productos: ${productsResponse.errorBody()?.string()}")
                )
            }

            val productContainer = productsResponse.body()
                ?: return Result.failure(Exception("Respuesta de productos vacía"))

            val products = productContainer.data
            Log.e(">>>", products.toString())

            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProduct(itemId: Int): Result<ProductData> {
        return try {
            val productResponse = apiService.getProduct(itemId)
            if (!productResponse.isSuccessful) {
                return Result.failure(
                    Exception("Error al obtener producto: ${productResponse.errorBody()?.string()}")
                )
            }

            val productContainer = productResponse.body()
                ?: return Result.failure(Exception("Respuesta del producto vacía"))

            val product = productContainer.data
            Log.e(">>>", product.toString())

            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProductsByProfileId(profileId: Int): Result<List<ProductData>> {
        return try {
            val productResponse = apiService.getProductsByProfileId(profileId)
            if (!productResponse.isSuccessful) {
                return Result.failure(
                    Exception("Error al obtener productos por perfil: ${productResponse.errorBody()?.string()}")
                )
            }

            val productContainer = productResponse.body()
                ?: return Result.failure(Exception("Respuesta de productos vacía"))

            val products = productContainer.data
            Log.e(">>>", products.toString())

            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
