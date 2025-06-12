package com.example.usolo.features.rental_registration.data.sources

import com.example.usolo.features.rental_registration.data.dto.DirectusResponse
import com.example.usolo.features.rental_registration.data.dto.ReservationWithItemDTO
import com.example.usolo.features.rental_registration.domain.model.RentalItem
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RentalRegistrationService {

    @GET("/items/item")
    suspend fun getMyPublishedItems(
        @Header("Authorization") token: String,
        @Query("filter[profile_id][_eq]") profileId: String,
        @Query("fields") fields: String = "*"
    ): DirectusResponse<List<RentalItem>>

    @GET("/items/reservation")
    suspend fun getMyRentedItems(
        @Header("Authorization") token: String,
        @Query("filter[profile_id][_eq]") profileId: String,
        @Query("filter[item_id][_nnull]") notNull: String = "true",
        @Query("fields") fields: String = "id,start_date,completion_date,total_price,item_id.*"
    ): DirectusResponse<List<ReservationWithItemDTO>>


}