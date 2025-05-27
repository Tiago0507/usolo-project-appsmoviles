package com.example.usolo.features.postobject.data.repository

import com.example.usolo.features.postobject.domain.model.RentProduct

interface RentProductRepository {
    suspend fun publishProduct(product: RentProduct, token: String): Result<Unit>
}
