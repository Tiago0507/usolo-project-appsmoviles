package com.example.usolo.features.rental_registration.data.repository
import com.example.usolo.features.rental_registration.domain.model.RentalItem

interface RentalRegistrationRepository {
    suspend fun getMyPublishedItems(userId: String): Result<List<RentalItem>>
    suspend fun getMyRentedItems(userId: String): Result<List<RentalItem>>
}