package com.example.usolo.features.products.data.dto

data class ProductUpdateDto (
    val title:String,
    val description:String,
    val pricePerDay:Number,
    val category:String,
    val status:String,
    val photoUrl:String
)

data class ProductData(
    val title:String,
    val description:String,
    val pricePerDay:Number,
    val category:String,
    val status:String,
    val photoUrl:String,
    val availability:Int
)