package com.example.usolo.features.rental_registration.data.repository.impl

import android.util.Log
import com.example.usolo.config.RetrofitConfig
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.rental_registration.data.repository.RentalRegistrationRepository
import com.example.usolo.features.rental_registration.data.sources.RentalRegistrationService
import com.example.usolo.features.rental_registration.domain.model.RentalItem
import kotlinx.coroutines.flow.firstOrNull

class RentalRegistrationRepositoryImpl(
    private val service: RentalRegistrationService = RetrofitConfig.directusRetrofit.create(RentalRegistrationService::class.java)
): RentalRegistrationRepository {

    override suspend fun getMyPublishedItems(userId: String): Result<List<RentalItem>> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
            val response = service.getMyPublishedItems("Bearer $token", userId)
            Log.d("RentalRepo", "Published items response: ${response.data.size} items")
            Result.success(response.data)
        } catch (e: Exception) {
            Log.e("RentalRepo", "Error getting published items: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun getMyRentedItems(userId: String): Result<List<RentalItem>> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
            val response = service.getMyRentedItems("Bearer $token", userId)

            Log.d("RentalRepo", "Rented items raw response: ${response.data}")

            val items = response.data.map { reservation ->
                Log.d("RentalRepo", "Processing reservation: ${reservation.id} with item: ${reservation.item}")
                reservation.item
            }

            Log.d("RentalRepo", "Rented items final: ${items.size} items")
            Result.success(items)
        } catch (e: Exception) {
            Log.e("RentalRepo", "Error getting rented items: ${e.message}", e)
            Result.failure(e)
        }
    }
}