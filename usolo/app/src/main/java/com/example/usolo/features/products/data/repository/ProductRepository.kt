package com.example.usolo.features.products.data.repository

import com.example.authclass10.config.RetrofitConfig
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import com.example.usolo.features.products.data.sources.ProductApi

class ProductRepository {

    private val apiService = RetrofitConfig.directusRetrofit.create(ProductApi::class.java)

    suspend fun updateProduct(itemId:Int, updateDto: ProductUpdateDto, token: String) : Result<ProductData>{
        return try{

            val productUpdate = ProductUpdateDto(
                title = updateDto.title,
                description = updateDto.description,
                price_per_day = updateDto.price_per_day,
                category_id = updateDto.category_id,
                status_id = updateDto.status_id,
                photo = updateDto.photo
            )

            val bearerToken = "Bearer $token"
            val productResponse = apiService.updateProduct(productUpdate,itemId,bearerToken)
            if(!productResponse.isSuccessful){
                return Result.failure(
                    java.lang.Exception(
                        "Error al actualizar el producto: ${
                            productResponse.errorBody()?.string()
                        }"
                    )
                )
            }

            val productContainer = productResponse.body()?: return Result.failure(Exception("Respuesta del prodcuto vacia"))
            val product = productContainer.data


            Result.success(
                ProductData(
                    title = product.title,
                    description = product.description,
                    price_per_day = product.price_per_day,
                    category_id = product.category_id,
                    status_id = product.status_id,
                    photo = product.photo,
                    profile_id = product.profile_id,
                    availability = product.availability
                )
            )
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun deleteProduct(itemId:Int,token: String) {
        val bearerToken = "Bearer $token"
        apiService.deleteProduct(itemId, bearerToken)
    }
}