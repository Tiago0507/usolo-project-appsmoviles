package com.example.usolo.features.products.data.repository

import com.example.usolo.config.RetrofitConfig
import com.example.usolo.domain.model.Review
import com.example.usolo.features.auth.data.dto.UserProfileResponseDTO
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ReviewData
import com.example.usolo.features.products.data.sources.ProductApi

interface ProductDetailRepository {
    suspend fun getProductDetails(itemId: Int, token: String): ProductData?
    suspend fun getProductReviews(itemId: Int, token: String): List<ReviewData>?
    suspend fun getUsersFromReviews(reviews: List<ReviewData>, token: String):List<String>?
    suspend fun createReview(rating: Float, comment: String, publicationDate: String, itemId: Int, profileId: Int, token: String): Result<ReviewData>

}

