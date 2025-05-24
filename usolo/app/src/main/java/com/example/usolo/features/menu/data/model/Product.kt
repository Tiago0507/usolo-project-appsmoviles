package com.example.usolo.features.menu.data.model

data class Product(
    val name: String,
    val pricePerDay: String,
    val imageRes: Int
)
data class ProductListContainer(
    val data: List<ProductData>
)

data class ProductContainer(
    val data: ProductData
)

data class ProductData(
    val id: String,
    val title: String,
    val description: String,
    val price_per_day: Number,
    val location: String,
    val category_id: String,
    val status_id: String,
    val photo: String,
    val profile_id: Int,
    val availability: Boolean
)
//-------------------------------------------------------------------------------------------------------------------
// DTOs para Category
data class CategoryData(
    val id: Int,
    val name: String
)

data class CategoryContainer(
    val data: CategoryData
)

data class CategoryListContainer(
    val data: List<CategoryData>
)

// DTOs para Status
data class StatusData(
    val id: Int,
    val name: String
)

data class StatusContainer(
    val data: StatusData
)

data class StatusListContainer(
    val data: List<StatusData>
)

// DTOs para Review
data class ReviewData(
    val id: Int,
    val rating: Int,
    val comment: String,
    val publication_date: String,
    val profile_id: Int,
    val item_id: Int
)
data class Review(
    val userName: String,
    val rating: Int,
    val comment: String
)

data class ReviewContainer(
    val data: ReviewData
)

data class ReviewListContainer(
    val data: List<ReviewData>
)
data class ProductWithUser(
    val product: ProductData,
    val userName: String,
    val userPhoto: String? = null
)