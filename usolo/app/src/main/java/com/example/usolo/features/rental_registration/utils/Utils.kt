package com.example.usolo.features.rental_registration.utils

fun String?.toDirectusImageUrl(): String? {
    val baseUrl = "http://localhost:8055" // O la del emulador 10.0.2.2
    return this?.let { "$baseUrl/assets/$it" }
}