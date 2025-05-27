package com.example.usolo.features.products.data.repository
import com.example.usolo.features.products.data.dto.*

interface ProductRepository {
    suspend fun updateProduct(itemId: Int, updateDto: ProductUpdateDto): Result<ProductData>
    suspend fun deleteProduct(itemId: Int)
    suspend fun getItemStatuses(): List<ItemStatus>
    suspend fun getCategories(): List<Category>
    suspend fun getProduct(itemId: Int): ProductData
}
