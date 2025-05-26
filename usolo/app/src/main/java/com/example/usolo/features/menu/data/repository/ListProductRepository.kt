package com.example.usolo.features.menu.data.repository

import android.util.Log
import com.example.authclass10.config.RetrofitConfig
import com.example.usolo.features.menu.data.model.CategoryData
import com.example.usolo.features.menu.data.model.ProductData
import com.example.usolo.features.menu.data.model.ProductContainer
import com.example.usolo.features.menu.data.model.ProductListContainer
import com.example.usolo.features.menu.data.model.ProductWithUser
import com.example.usolo.features.menu.data.model.ReviewData
import com.example.usolo.features.menu.data.model.StatusData
import com.example.usolo.features.menu.data.model.UserProfileData
import com.example.usolo.features.products.data.sources.ProductApi

class ListProductRepository {

    private val apiService = RetrofitConfig.directusRetrofit.create(ProductApi::class.java)

    suspend fun getProducts(): Result<List<ProductWithUser>> {
        return try {
            val productsResponse = apiService.getProducts()
            if (!productsResponse.isSuccessful) {
                return Result.failure(
                    Exception(
                        "Error al obtener productos: ${
                            productsResponse.errorBody()?.string()
                        }"
                    )
                )
            }

            val productContainer = productsResponse.body() ?: return Result.failure(
                Exception("Respuesta de productos vacía")
            )

            val allProducts = productContainer.data
            Log.e(">>>", allProducts.toString())

            val enabledProducts = allProducts.filter { it.availability }
            Log.e(">>>", "Productos habilitados: ${enabledProducts.size}")

            val fullProducts = enabledProducts.mapNotNull { product ->
                try {
                    val profileResult = getUserProfile(product.profile_id).getOrNull()
                    if (profileResult == null) {
                        Log.w("ProductMapping", "Perfil no encontrado para product.userId=${product.profile_id}")
                        return@mapNotNull null
                    }

                    val userResponse = apiService.getUserDirectus(profileResult.user_id)
                    val userResult = userResponse.body()?.data
                    if (userResult == null) {
                        Log.w("ProductMapping", "Usuario Directus no encontrado para profile.userId=${profileResult.user_id}")
                        return@mapNotNull null
                    }

                    ProductWithUser(
                        product = product,
                        userName = userResult.first_name,
                        userPhoto = null
                    )
                } catch (e: Exception) {
                    Log.e("ProductMapping", "Error inesperado con producto: ${product.title} - ${e.message}", e)
                    null
                }
            }

            Log.e(">>>", "todo: ${fullProducts.size}")
            Result.success(fullProducts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserProfile(profileId: Int): Result<UserProfileData> {
        return try {
            val profileResponse = apiService.getUserProfile(profileId)
            if (!profileResponse.isSuccessful) {
                return Result.failure(
                    Exception("Error al obtener perfil: ${profileResponse.errorBody()?.string()}")
                )
            }

            val profileContainer = profileResponse.body() ?: return Result.failure(
                Exception("Respuesta de perfil vacía")
            )

            Result.success(profileContainer.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProduct(itemId: String): Result<ProductData> {
        return try {
            val productResponse = apiService.getProductsByProfileId(profileId)
            if (!productResponse.isSuccessful) {
                return Result.failure(
                    Exception(
                        "Error al obtener producto: ${
                            productResponse.errorBody()?.string()
                        }"
                    )
                )
            }

            val productContainer = productResponse.body() ?: return Result.failure(
                Exception("Respuesta del producto vacía")
            )

            // Accedemos a .data que contiene el producto individual
            val product = productContainer.data
            Log.e(">>>", product.toString())

            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getProductsByProfileId(profileId: Int): Result<List<ProductData>> {
        return try {
            val productResponse = apiService.getProductsByProfileId(profileId)
            if (!productResponse.isSuccessful) {
                return Result.failure(
                    Exception(
                        "Error al obtener producto: ${
                            productResponse.errorBody()?.string()
                        }"
                    )
                )
            }

            val productContainer = productResponse.body() ?: return Result.failure(
                Exception("Respuesta del producto vacía")
            )

            // Accedemos a .data que contiene el producto individual
            val product = productContainer.data
            Log.e(">>>", product.toString())

            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------------------
    suspend fun getCategory(categoryId: Int): Result<CategoryData> {
        return try {
            val categoryResponse = apiService.getCategory(categoryId)
            if (!categoryResponse.isSuccessful) {
                return Result.failure(
                    Exception("Error al obtener categoría: ${categoryResponse.errorBody()?.string()}")
                )
            }

            val categoryContainer = categoryResponse.body() ?: return Result.failure(
                Exception("Respuesta de categoría vacía")
            )

            Result.success(categoryContainer.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun getStatus(statusId: String): Result<StatusData> {
        return try {
            val statusResponse = apiService.getStatus(statusId)
            if (!statusResponse.isSuccessful) {
                return Result.failure(
                    Exception("Error al obtener status: ${statusResponse.errorBody()?.string()}")
                )
            }

            val statusContainer = statusResponse.body() ?: return Result.failure(
                Exception("Respuesta de status vacía")
            )

            Result.success(statusContainer.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    suspend fun getReviewsByItem(itemId: String): Result<List<ReviewData>> {
        return try {
            val reviewsResponse = apiService.getReviewsByItem(itemId)
            if (!reviewsResponse.isSuccessful) {
                return Result.failure(
                    Exception("Error al obtener reviews: ${reviewsResponse.errorBody()?.string()}")
                )
            }

            val reviewsContainer = reviewsResponse.body() ?: return Result.failure(
                Exception("Respuesta de reviews vacía")
            )

            Result.success(reviewsContainer.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}