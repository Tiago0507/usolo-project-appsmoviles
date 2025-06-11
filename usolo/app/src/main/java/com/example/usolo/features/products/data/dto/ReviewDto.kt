package com.example.usolo.features.products.data.dto

import java.util.Date

// Represents a single review
data class ReviewData(
    val id: Int, // Unique ID of the review
    val rating: Float, // Rating given, e.g., 4.5
    val comment: String, // The review text
    val publication_date: String, // Date of publication in ISO 8601 format
    val profile_id: Int, // Name of the person who wrote the review
    val item_id: Int 

)

// Wrapper for the list of reviews from Directus
data class DirectusResponseReviews<T>(
    val data: List<T>
)
