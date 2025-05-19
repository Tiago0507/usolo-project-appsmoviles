package com.example.usolo.features.products.data.sources

import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ProductApi {

    @GET("items/item")
    suspend fun getProducts() : Response<ProductData>

    @GET("items/item/{itemId}")
    suspend fun getProduct(@Path("itemId") itemId: Int) : Response<ProductData>

    @PATCH("items/item/{itemId}")
    suspend fun updateProduct(
        @Body updateDto: ProductUpdateDto,
        @Path("itemId") itemId: Int
    ) : Response<ProductData>

    @DELETE("items/item/{itemId}")
    suspend fun deleteProduct(
        @Path("itemId") itemId: Int
    )

}