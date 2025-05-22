package com.example.usolo.features.rental_registration.data.repository
import com.example.usolo.features.rental_registration.data.dto.RentalItemDTO

interface RentalRegistrationRepository {
    suspend fun getMyPublishedItems(): Result<List<RentalItemDTO>>
    suspend fun getMyRentedItems(): Result<List<RentalItemDTO>>
}