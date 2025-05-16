package com.example.usolo.features.products.data.sources

import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface ProductApi {

    @GET("items/products")
    suspend fun getProducts() : Response<ProductData>

    @PUT("items/update/product/:id")
    suspend fun updateProduct(
        @Body updateDto: ProductUpdateDto
    ) : Response<ProductData>

}