package com.example.usolo.features.rental_registration.data.dto
import com.example.usolo.features.rental_registration.domain.model.*
import com.squareup.moshi.Json

data class ReservationWithItemDTO(
    val id: Int,
    @Json(name = "profile_id") val profileId: Int? = null,
    @Json(name = "start_date") val startDate: String,
    @Json(name = "completion_date") val completionDate: String?,
    @Json(name = "total_price") val totalPrice: String,
    @Json(name = "item_id") val item: RentalItem // Aquí es un objeto
)
