package com.example.usolo.features.products.data.repository

import com.example.usolo.config.RetrofitConfig
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.products.data.dto.*
import com.example.usolo.features.products.data.sources.ProductApi
import kotlinx.coroutines.flow.first

class ProductRepository {

    private val apiService = RetrofitConfig.directusRetrofit.create(ProductApi::class.java)

    suspend fun updateProduct(itemId: Int, updateDto: ProductUpdateDto): Result<ProductData> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").first()
            val bearerToken = "Bearer $token"

            val response = apiService.updateProduct(updateDto, itemId, bearerToken)

            if (!response.isSuccessful) {
                return Result.failure(
                    Exception("Error al actualizar el producto: ${response.errorBody()?.string()}")
                )
            }

            val wrapper = response.body()
                ?: return Result.failure(Exception("Respuesta vacía del servidor"))

            val product = wrapper.data

            Result.success(product)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteProduct(itemId: Int) {
        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val response = apiService.deleteProduct(itemId, bearerToken)
        if (!response.isSuccessful) {
            throw Exception("Error al eliminar producto: ${response.errorBody()?.string()}")
        }
    }

    suspend fun getItemStatuses(): List<ItemStatus> {
        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val response = apiService.getItemStatuses(bearerToken)
        return response.data
    }

    suspend fun getCategories(): List<Category> {
        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val response = apiService.getCategories(bearerToken)
        return response.data
    }

    suspend fun getProduct(itemId: Int): ProductData {
        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val response = apiService.getProduct(itemId, bearerToken)

        return response.data
    }
}
