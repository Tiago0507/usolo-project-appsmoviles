package com.example.usolo.features.products.data.dto

// Respuesta genérica de Directus
data class DirectusResponseProducts<T>(
    val data: List<T>
)

// DTO para actualizar un producto
data class ProductUpdateDto(
    val title: String,
    val description: String,
    val price_per_day: Double,
    val category_id: Int,
    val status_id: Int,
    val photo: String
)

// Modelo de producto completo
data class ProductData(
    val id: Int,
    val title: String,
    val description: String,
    val price_per_day: Double,
    val category_id: Int,
    val status_id: Int,
    val profile_id: Int,
    val photo: String,
    val availability: Boolean
)

data class ReservationData(
    val id: Int,
    val item_id: Int,
    val profile_id: Int,
    val start_date: String?,          // ← antes: String
    val completion_date: String?,
    val total_price: String?,
    val status_id: Int?
)


// Modelo de status de un producto (activo, inactivo, etc.)
data class StatusResponse(
    val data: List<ItemStatus>
)

data class ItemStatus(
    val id: Int,
    val name: String
)

// Modelo de categoría de producto
data class CategoryResponse(
    val data: List<Category>
)

data class Category(
    val id: Int,
    val name: String
)

object ProductHelper {
    private var tempProduct: ProductData? = null

    fun setCurrentProduct(product: ProductData) {
        tempProduct = product
    }

    fun getCurrentProduct(): ProductData? {
        return tempProduct
    }
}
