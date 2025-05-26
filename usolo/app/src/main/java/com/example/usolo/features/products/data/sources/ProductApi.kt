package com.example.usolo.features.products.data.sources

import com.example.usolo.features.products.data.dto.*
import com.example.usolo.features.registration.data.dto.DirectusResponse
import retrofit2.Response
import retrofit2.http.*

interface ProductApi {

    @GET("items/item")
    suspend fun getProducts(): Response<DirectusResponseProducts<ProductData>>

    @GET("items/item")
    suspend fun getProductsByProfileId(
        @Query("filter[profile_id][id][_eq]") profileId: Int
    ): Response<DirectusResponseProducts<ProductData>>

    @GET("items/item/{itemId}")
    suspend fun getProduct(
        @Path("itemId") itemId: Int,
        @Header("Authorization") token: String
    ): DirectusResponse<ProductData>

    @PATCH("items/item/{itemId}")
    suspend fun updateProduct(
        @Body updateDto: ProductUpdateDto,
        @Path("itemId") itemId: Int,
        @Header("Authorization") token: String
    ): Response<DirectusResponse<ProductData>>

    @DELETE("items/item/{itemId}")
    suspend fun deleteProduct(
        @Path("itemId") itemId: Int,
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET("items/item_status")
    suspend fun getItemStatuses(
        @Header("Authorization") token: String
    ): StatusResponse

    @GET("items/category")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): CategoryResponse
}
