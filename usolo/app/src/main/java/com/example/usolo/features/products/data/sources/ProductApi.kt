package com.example.usolo.features.products.data.sources

import com.example.usolo.features.auth.data.dto.DirectusListResponse
import com.example.usolo.features.auth.data.dto.UserProfileWithNameDTO
import com.example.usolo.features.products.data.dto.*
import com.example.usolo.features.registration.data.dto.DirectusResponse
import retrofit2.Response
import retrofit2.http.*

data class DeleteQuery(
    val query: Map<String, Map<String, Any>>
)

interface ProductApi {

    @GET("items/item")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): DirectusResponseProducts<ProductData>

    @GET("items/item")
    suspend fun getProductsByProfileId(
        @Query("filter[profile_id][_eq]") profileId: Int,
        @Header("Authorization") token: String
    ): DirectusResponseProducts<ProductData>

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

    @GET("items/review")
    suspend fun getReviewsByProductId(
        @Query("filter[item_id][_eq]") itemId: Int,
        @Header("Authorization") token: String
    ): DirectusResponseReviews<ReviewData>

    @GET("/items/user_profile")
    suspend fun getUserProfileWithUserName(
        @Header("Authorization") token: String,
        @Query("filter[id][_eq]") profileId: Int,
        @Query("fields") fields: String = "user_id.first_name"
    ): DirectusListResponse<UserProfileWithNameDTO>

    @POST("items/review")
    suspend fun createReview(
        @Header("Authorization") token: String,
        @Body reviewDto: CreateReviewDto
    ): DirectusResponse<ReviewData>

    @DELETE("items/review")
    suspend fun deleteReviewsByProductId(
        @QueryMap filter: Map<String, String>,
        @Header("Authorization") token: String
    ): Response<Unit>

    // junto a DeleteQuery...
    @HTTP(method = "DELETE", path = "items/reservation", hasBody = true)
    suspend fun deleteReservationsByProductId(
        @Body deleteQuery: DeleteQuery,
        @Header("Authorization") token: String
    ): Response<Void>

    @HTTP(method = "DELETE", path = "items/payment", hasBody = true)
    suspend fun deletePaymentsByReservationId(
        @Body deleteQuery: DeleteQuery,
        @Header("Authorization") token: String
    ): Response<Void>

    @HTTP(method = "DELETE", path = "items/review", hasBody = true)
    suspend fun deleteReviewsByProductId(
        @Body deleteQuery: DeleteQuery,
        @Header("Authorization") token: String
    ): Response<Void>    // <— aquí cambia a Void

    @GET("items/reservation")
    suspend fun getReservationsByProductId(
        @Query("filter[item_id][_eq]") itemId: Int,
        @Header("Authorization") token: String,
        @Query("filter[profile_id][_nnull]") notNull: Boolean = true,
        @Query("fields") fields: String = "id,profile_id"
    ): DirectusListResponse<ReservationData>

}