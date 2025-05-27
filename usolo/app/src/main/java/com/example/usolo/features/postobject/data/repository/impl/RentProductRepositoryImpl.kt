package com.example.usolo.features.postobject.data.repository

import com.example.usolo.config.RetrofitConfig
import com.example.usolo.features.postobject.domain.model.RentProduct
import com.example.usolo.features.postobject.data.sources.PostObjectService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RentProductRepositoryImpl(
    private val api: PostObjectService = RetrofitConfig.directusRetrofit.create(PostObjectService::class.java)
): RentProductRepository {
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8055/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override suspend fun publishProduct(product: RentProduct, token: String): Result<Unit> {
        return try {
            val response = api.createRentProduct(product, "Bearer $token")
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
