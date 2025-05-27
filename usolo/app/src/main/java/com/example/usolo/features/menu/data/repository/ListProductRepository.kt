package com.example.usolo.features.menu.data.repository
import com.example.usolo.features.products.data.dto.ProductData

interface ListProductRepository {
    suspend fun getProducts(): Result<List<ProductData>>
    suspend fun getProduct(itemId: Int): Result<ProductData>
    suspend fun getProductsByProfileId(profileId: Int): Result<List<ProductData>>
}
