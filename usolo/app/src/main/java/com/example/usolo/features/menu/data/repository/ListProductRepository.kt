package com.example.usolo.features.menu.data.repository
import com.example.usolo.config.RetrofitConfig
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.sources.ProductApi

import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import kotlinx.coroutines.flow.first

class ListProductRepository {

    private val apiService = RetrofitConfig.directusRetrofit.create(ProductApi::class.java)

    suspend fun getProducts(): Result<List<ProductData>> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").first()
            val response = apiService.getProducts("Bearer $token")
            val products = response.data
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProduct(itemId: Int): Result<ProductData> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").first()
            val response = apiService.getProduct(itemId, "Bearer $token")
            val product = response.data
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProductsByProfileId(profileId: Int): Result<List<ProductData>> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").first()
            val response = apiService.getProductsByProfileId(profileId, "Bearer $token")
            val products = response.data
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
