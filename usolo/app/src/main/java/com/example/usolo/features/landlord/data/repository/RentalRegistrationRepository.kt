package com.example.usolo.features.landlord.data.repository

import com.example.usolo.R
import com.example.usolo.features.landlord.data.dto.ItemDTO

class RentalRegistrationRepository {
    fun getPublishedProducts(): List<ItemDTO> {
        return listOf(
            ItemDTO("Cámara GoPro", "$30.000 por día", R.drawable.silla_gamer)
        )
    }

    fun getRentedProducts(): List<ItemDTO> {
        return listOf(
            ItemDTO("Kamera Sony X", "$40.000 por día", R.drawable.silla_gamer)
        )
    }
}