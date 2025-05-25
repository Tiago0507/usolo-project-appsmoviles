package com.example.usolo.features.rental_registration.data.repository.impl

import com.example.authclass10.config.RetrofitConfig
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
            Result.success(response.data)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMyRentedItems(userId: String): Result<List<RentalItem>> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
            val response = service.getMyRentedItems("Bearer $token", userId)
            val items = response.data.map { it.item }
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}