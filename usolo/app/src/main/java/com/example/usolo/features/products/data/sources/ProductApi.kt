package com.example.usolo.features.products.data.sources

import com.example.usolo.features.products.data.dto.ProductListResponse
import com.example.usolo.features.products.data.dto.ProductResponse
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {

    @GET("items/item")
    suspend fun getProducts(): Response<ProductListResponse>

    // ← Aquí quité la llave sobrante al final de la línea
    @GET("items/item/{itemId}")
    suspend fun getProduct(
        @Path("itemId") itemId: Int
    ): Response<ProductResponse>

    @PATCH("items/item/{itemId}")
    suspend fun updateProduct(
        @Path("itemId") itemId: Int,
        @Body updateDto: ProductUpdateDto
    ): Response<ProductResponse>

    @DELETE("items/item/{itemId}")
    suspend fun deleteProduct(
        @Path("itemId") itemId: Int
    ): Response<Unit>
}
