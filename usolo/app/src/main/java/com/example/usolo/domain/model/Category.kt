package com.example.usolo.domain.model

data class Category(
    val id: Int,
    val name: String,
    val items: List<Item>
)
