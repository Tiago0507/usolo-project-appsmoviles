package com.example.usolo.features.postobject.domain.repository

import com.example.usolo.features.postobject.data.model.RentProduct
import com.example.usolo.features.postobject.data.model.remote.DirectusApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RentProductRepository {

    private val api: DirectusApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8055/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(DirectusApi::class.java)
    }

    suspend fun publishProduct(product: RentProduct, token: String): Result<Unit> {
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
