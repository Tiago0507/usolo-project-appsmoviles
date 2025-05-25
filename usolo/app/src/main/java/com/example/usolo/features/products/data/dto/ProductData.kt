package com.example.usolo.features.products.data.dto

data class DirectusResponseProducts<T>(
    val data: List<T>
)

data class ProductUpdateDto (
    val title:String,
    val description:String,
    val price_per_day:Number,
    val category_id:Int,
    val status_id:Int,
    val photo:String,
)

data class ProductData(
    val id: Int,
    val title:String,
    val description:String,
    val price_per_day:Number,
    val category_id:Int,
    val status_id:Int,
    val profile_id:Int,
    val photo:String,
    val availability:Boolean
)

data class StatusResponse(
    val data: List<ItemStatus>
)

data class ItemStatus(
    val id: Int,
    val name: String
)

data class CategoryResponse(
    val data: List<Category>
)
data class Category(
    val id: Int,
    val name: String
)
