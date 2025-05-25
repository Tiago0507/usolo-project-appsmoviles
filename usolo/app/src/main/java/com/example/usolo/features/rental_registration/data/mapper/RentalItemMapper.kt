package com.example.usolo.features.rental_registration.data.mapper

import com.example.usolo.features.rental_registration.data.dto.RentalItemDTO
import com.example.usolo.features.rental_registration.domain.model.RentalItem

fun RentalItemDTO.toDomain(): RentalItem {
    return RentalItem(
        id = id,
        title = title,
        description = description,
        pricePerDay = pricePerDay,
        location = location,
        availability = availability,
        photo = photo,
        profileId = profileId,
        categoryId = categoryId,
        statusId = statusId
    )
}


fun RentalItem.toDto(): RentalItemDTO {
    return RentalItemDTO(
        id = id,
        title = title,
        description = description,
        pricePerDay = pricePerDay,
        location = location,
        availability = availability,
        photo = photo,
        profileId = profileId,
        categoryId = categoryId,
        statusId = statusId
    )
}
