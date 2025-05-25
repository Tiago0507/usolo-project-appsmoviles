package com.example.usolo.features.rental_registration.data.dto
import com.example.usolo.features.rental_registration.domain.model.*
import com.squareup.moshi.Json

data class ReservationWithItemDTO(
    val id: Int,
    @Json(name = "start_date") val startDate: String,
    @Json(name = "completion_date") val completionDate: String?,
    @Json(name = "total_price") val totalPrice: String,
    //val item: RentalItem
    @Json(name = "item2") val item: RentalItem
)
