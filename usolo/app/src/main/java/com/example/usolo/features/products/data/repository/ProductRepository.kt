package com.example.usolo.features.products.data.repository

import android.util.Log
import com.example.authclass10.config.RetrofitConfig
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.products.data.dto.Category
import com.example.usolo.features.products.data.dto.ItemStatus
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import com.example.usolo.features.products.data.sources.ProductApi
import kotlinx.coroutines.flow.first
import kotlin.math.log

class ProductRepository {

    private val apiService = RetrofitConfig.directusRetrofit.create(ProductApi::class.java)

    suspend fun updateProduct(userId:Int, updateDto: ProductUpdateDto) : Result<ProductData>{
        return try{

            val productUpdate = ProductUpdateDto(
                title = updateDto.title,
                description = updateDto.description,
                price_per_day = updateDto.price_per_day,
                category_id = updateDto.category_id,
                status_id = updateDto.status_id,
                photo = updateDto.photo
            )
            val token = LocalDataSourceProvider.get().load("accesstoken").first()
            val bearerToken = "Bearer $token"

            val productResponse = apiService.updateProduct(productUpdate,userId,bearerToken)
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
                    id = product.id,
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

    suspend fun deleteProduct(itemId:Int) {
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

    suspend fun getProduct(itemId: Int): ProductData{

        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val result = apiService.getProduct(itemId,bearerToken)
        println(bearerToken)
        val product = result.data

        return product
    }
}