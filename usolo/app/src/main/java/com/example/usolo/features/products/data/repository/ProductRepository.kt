package com.example.usolo.features.products.data.repository

import com.example.authclass10.config.RetrofitConfig
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import com.example.usolo.features.products.data.sources.ProductApi

class ProductRepository {

    private val apiService =
        RetrofitConfig.directusRetrofit.create(ProductApi::class.java)

    suspend fun updateProduct(
        itemId: Int,
        updateDto: ProductUpdateDto
    ): Result<ProductData> {
        return try {
            // Llamada al API con parámetros en el orden correcto
            val resp = apiService.updateProduct(itemId, updateDto)
            if (!resp.isSuccessful) {
                return Result.failure(Exception("Error al actualizar: ${resp.errorBody()?.string()}"))
            }

            // Aquí .body() es ProductResponse, así que sacamos .data
            val wrapper = resp.body()
                ?: return Result.failure(Exception("Respuesta vacía del servidor"))

            val dto = wrapper.data

            // Y devolvemos tu DTO original
            Result.success(
                ProductData(
                    title        = dto.title,
                    description  = dto.description,
                    pricePerDay  = dto.pricePerDay,
                    category     = dto.category,
                    status       = dto.status,
                    photoUrl     = dto.photoUrl,
                    availability = dto.availability
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteProduct(itemId:Int) {
        apiService.deleteProduct(itemId)
    }
}
