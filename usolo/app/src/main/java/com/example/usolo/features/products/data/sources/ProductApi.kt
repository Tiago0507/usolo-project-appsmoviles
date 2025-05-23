package com.example.usolo.features.products.data.sources

import com.example.usolo.features.menu.data.model.CategoryContainer
import com.example.usolo.features.menu.data.model.CategoryListContainer
import com.example.usolo.features.menu.data.model.DirectusResponse
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import com.example.usolo.features.menu.data.model.ProductListContainer
import com.example.usolo.features.menu.data.model.ProductContainer
import com.example.usolo.features.menu.data.model.ReviewListContainer
import com.example.usolo.features.menu.data.model.StatusContainer
import com.example.usolo.features.menu.data.model.StatusListContainer
import com.example.usolo.features.menu.data.model.UserProfileContainer
import com.example.usolo.features.menu.data.model.UserResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("items/item")
    suspend fun getProducts() : Response<ProductListContainer>

    @GET("items/item/{itemId}")
    suspend fun getProduct(@Path("itemId") itemId: String) : Response<ProductContainer>

    // Endpoints de categorías
    @GET("items/category")
    suspend fun getCategories() : Response<CategoryListContainer>

    @GET("items/category/{categoryId}")
    suspend fun getCategory(@Path("categoryId") categoryId: Int) : Response<CategoryContainer>

    // Endpoints de status
    @GET("items/item_status")
    suspend fun getStatuses() : Response<StatusListContainer>

    @GET("items/item_status/{statusId}")
    suspend fun getStatus(@Path("statusId") statusId: String) : Response<StatusContainer>

    // Endpoints de user profile
    @GET("items/user_profile/{profileId}")
    suspend fun getUserProfile(@Path("profileId") profileId: Int) : Response<UserProfileContainer>

    @GET("users/{id}")
    suspend fun getUserDirectus(@Path("id") id:String ): Response<DirectusResponse<UserResponseDTO>>

    // Endpoints de reviews
    @GET("items/review")
    suspend fun getReviews() : Response<ReviewListContainer>

    @GET("items/review")
    suspend fun getReviewsByItem(@Query("filter[item_id][_eq]") itemId: String) : Response<ReviewListContainer>


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