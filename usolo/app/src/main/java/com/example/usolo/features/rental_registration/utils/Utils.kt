package com.example.usolo.features.rental_registration.utils


fun String?.toDirectusImageUrl(): String? {
    val baseUrl = "http://10.0.2.2:8055"
    return this?.let { "$baseUrl/assets/$it" }
}
