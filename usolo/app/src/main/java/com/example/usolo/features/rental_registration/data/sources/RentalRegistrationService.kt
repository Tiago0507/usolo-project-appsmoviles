package com.example.usolo.features.rental_registration.data.sources

import com.example.usolo.features.rental_registration.domain.model.RentalItem
import retrofit2.http.GET
import retrofit2.http.Header

interface RentalRegistrationService {
    @GET("/items/my_published_items")
    suspend fun getMyPublishedItems(
        @Header("Authorization") token: String
    ): List<RentalItem>

    @GET("items/my_rentals")
    suspend fun getMyRentedItems(
        @Header("Authorization") token: String
    ): List<RentalItem>
}