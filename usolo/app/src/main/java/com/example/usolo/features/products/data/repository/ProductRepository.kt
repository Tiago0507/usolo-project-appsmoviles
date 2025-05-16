package com.example.usolo.features.products.data.repository

import com.example.authclass10.config.RetrofitConfig
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import com.example.usolo.features.products.data.sources.ProductApi

class ProductRepository {

    private val apiService = RetrofitConfig.directusRetrofit.create(ProductApi::class.java)

    suspend fun updateProduct(updateDto: ProductUpdateDto) : Result<ProductData>{
        return try{
            val productUpdate = ProductUpdateDto(
                title = updateDto.title,
                description = updateDto.description,
                pricePerDay = updateDto.pricePerDay,
                category = updateDto.category,
                status = updateDto.status,
                photoUrl = updateDto.photoUrl
            )

            val productResponse = apiService.updateProduct(productUpdate)
            if(!productResponse.isSuccessful){
                return Result.failure(
                    java.lang.Exception(
                        "Error al crear usuario: ${
                            productResponse.errorBody()?.string()
                        }"
                    )
                )
            }

            val product = productResponse.body()?: return Result.failure(Exception("Respuesta del prodcuto vacia"))


            Result.success(
                ProductData(
                    title = product.title,
                    description = product.description,
                    pricePerDay = product.pricePerDay,
                    category = product.category,
                    status = product.status,
                    photoUrl = product.photoUrl,
                    availability = product.availability
                )
            )
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}