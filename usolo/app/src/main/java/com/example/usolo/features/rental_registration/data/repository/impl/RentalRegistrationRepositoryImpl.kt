package com.example.usolo.features.rental_registration.data.repository.impl

import com.example.authclass10.config.RetrofitConfig
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.rental_registration.data.dto.RentalItemDTO
import com.example.usolo.features.rental_registration.data.mapper.toDto
import com.example.usolo.features.rental_registration.data.repository.RentalRegistrationRepository
import com.example.usolo.features.rental_registration.data.sources.RentalRegistrationService
import kotlinx.coroutines.flow.firstOrNull

class RentalRegistrationRepositoryImpl(
    private val service: RentalRegistrationService = RetrofitConfig.directusRetrofit.create(RentalRegistrationService::class.java)
): RentalRegistrationRepository {
    override suspend fun getMyPublishedItems(): Result<List<RentalItemDTO>> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
            val items = service.getMyPublishedItems("Bearer $token").map { it.toDto() }
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMyRentedItems(): Result<List<RentalItemDTO>> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
            val items = service.getMyRentedItems("Bearer $token").map { it.toDto() }
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}