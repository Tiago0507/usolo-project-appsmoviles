package com.example.usolo.features.products.data.repository.impl

import android.util.Log
import com.example.usolo.config.RetrofitConfig
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.products.data.dto.CreateReviewDto
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ReviewData
import com.example.usolo.features.products.data.repository.ProductDetailRepository
import com.example.usolo.features.products.data.sources.ProductApi

class ProductDetailRepositoryImpl(
    private val apiService: ProductApi = RetrofitConfig.directusRetrofit.create(ProductApi::class.java)
): ProductDetailRepository {

    override suspend fun getProductDetails(itemId: Int, token: String): ProductData? {
        return try {
            val response = apiService.getProduct(itemId, "Bearer $token")
            response.data
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getProductReviews(itemId: Int, token: String): List<ReviewData>? {
        return try {
            val response = apiService.getReviewsByProductId(itemId, "Bearer $token")
            response.data
        } catch (e: Exception) {
            Log.e("Repository", "Error al cargar reviews: ${e.message}")
            null
        }
    }

    override suspend fun getUsersFromReviews(reviews: List<ReviewData>, token: String): List<String>? {
        return try {
            val userNames = mutableListOf<String>()

            // Extraer profile_ids únicos de las reviews
            val uniqueProfileIds = reviews.map { it.profile_id }.distinct()

            // Obtener nombre de cada usuario
            for (profileId in uniqueProfileIds) {
                try {
                    val response = apiService.getUserProfileWithUserName(
                        token = "Bearer $token",
                        profileId = profileId
                    )

                    val name = response.data.firstOrNull()?.user_id?.first_name ?: "Desconocido"
                    userNames.add(name)
                } catch (e: Exception) {
                    // Si falla obtener un nombre específico, agregar "Desconocido"
                    userNames.add("Desconocido")
                }
            }

            userNames
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun createReview(
        rating: Float,
        comment: String,
        publicationDate: String,
        itemId: Int,
        profileId: Int,
        token: String
    ): Result<ReviewData> {
        return try {
            val reviewDto = CreateReviewDto(
                rating = rating,
                comment = comment,
                publication_date = publicationDate,
                profile_id = profileId,
                item_id = itemId
            )
            val response = apiService.createReview("Bearer $token", reviewDto)
            Result.success(response.data)
        } catch (e: Exception) {
            Log.e("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", "Error al crear review: ${e.message}")
            Result.failure(e)
        }
    }
}

