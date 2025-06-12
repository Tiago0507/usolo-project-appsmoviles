package com.example.usolo.domain.model

data class Review(
    val id: Int,
    val rating: Float,
    val comment: String,
    val publication_date: Long,
    val userId: Int,
    val productId: Int
)