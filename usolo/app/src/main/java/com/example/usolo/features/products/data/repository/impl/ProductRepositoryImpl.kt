package com.example.usolo.features.products.data.repository.impl

import com.example.usolo.config.RetrofitConfig
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.products.data.dto.Category
import com.example.usolo.features.products.data.dto.ItemStatus
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import com.example.usolo.features.products.data.repository.ProductRepository
import com.example.usolo.features.products.data.sources.ProductApi
import kotlinx.coroutines.flow.first

class ProductRepositoryImpl(
    private val productApi: ProductApi = RetrofitConfig.directusRetrofit.create(ProductApi::class.java)
): ProductRepository {

    override suspend fun updateProduct(itemId: Int, updateDto: ProductUpdateDto): Result<ProductData> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").first()
            val bearerToken = "Bearer $token"

            val response = productApi.updateProduct(updateDto, itemId, bearerToken)

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

    override suspend fun deleteProduct(itemId: Int) {
        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val response = productApi.deleteProduct(itemId, bearerToken)
        if (!response.isSuccessful) {
            throw Exception("Error al eliminar producto: ${response.errorBody()?.string()}")
        }
    }

    override suspend fun getItemStatuses(): List<ItemStatus> {
        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val response = productApi.getItemStatuses(bearerToken)
        return response.data
    }

    override suspend fun getCategories(): List<Category> {
        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val response = productApi.getCategories(bearerToken)
        return response.data
    }


    override suspend fun getProduct(itemId: Int): ProductData {
        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val response = productApi.getProduct(itemId, bearerToken)

        return response.data
    }
}