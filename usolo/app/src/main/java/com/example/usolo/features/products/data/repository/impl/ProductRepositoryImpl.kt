package com.example.usolo.features.products.data.repository.impl

import android.util.Log
import com.example.usolo.config.RetrofitConfig
import com.example.usolo.features.auth.data.repository.AuthRepository
import com.example.usolo.features.auth.data.repository.AuthRepositoryImpl
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.products.data.dto.Category
import com.example.usolo.features.products.data.dto.ItemStatus
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ProductUpdateDto
import com.example.usolo.features.products.data.repository.ProductRepository
import com.example.usolo.features.products.data.sources.DeleteQuery
import com.example.usolo.features.products.data.sources.ProductApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class ProductRepositoryImpl(
    private val productApi: ProductApi = RetrofitConfig.directusRetrofit.create(ProductApi::class.java) ,
    private val authRepository: AuthRepository = AuthRepositoryImpl()
): ProductRepository {

    override suspend fun updateProduct(itemId: Int, updateDto: ProductUpdateDto): Result<ProductData> {
        return try {
            val token = LocalDataSourceProvider.get().load("accesstoken").first()
            val bearerToken = "Bearer $token"

            val response = productApi.updateProduct(updateDto, itemId, bearerToken)

            if (!response.isSuccessful) {
                return Result.failure(
                    Exception("Error al actualizar el producto: ${response.errorBody()?.string()}")
                )
            }

            val wrapper = response.body()
                ?: return Result.failure(Exception("Respuesta vacía del servidor"))

            val product = wrapper.data

            Result.success(product)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProduct(itemId: Int) {
        try {
            val token = authRepository.getAccessToken() ?: throw Exception("Token no disponible")
            val bearer = "Bearer $token"

            // 1) Borrar reviews del producto
            val deleteReviewsBody = DeleteQuery(
                query = mapOf("item_id" to mapOf("_eq" to itemId))
            )
            val r1 = productApi.deleteReviewsByProductId(deleteReviewsBody, bearer)
            if (!r1.isSuccessful) throw Exception("No se pudieron borrar las reviews")

            // 2) Obtener el profileId del usuario actual
            val myProfileId = authRepository.getProfileId()?.toInt()
                ?: throw Exception("Perfil no disponible")

            // 3) Obtener SOLO las reservas hechas por el usuario actual para este item
            val reservationsResp = productApi.getReservationsByProductId(itemId, bearer)
            val myReservations = reservationsResp.data.filter { it.profile_id == myProfileId }
            val myReservationIds = myReservations.map { it.id }

            // 4) Borrar pagos asociados a esas reservas
            if (myReservationIds.isNotEmpty()) {
                val deletePaymentsBody = DeleteQuery(
                    query = mapOf("reservation_id" to mapOf("_in" to myReservationIds))
                )
                val r2 = productApi.deletePaymentsByReservationId(deletePaymentsBody, bearer)
                if (!r2.isSuccessful) throw Exception("No se pudieron borrar los pagos")
            }

            // 5) Borrar las reservas del usuario actual sobre este item
            if (myReservationIds.isNotEmpty()) {
                val deleteReservationsBody = DeleteQuery(
                    query = mapOf(
                        "item_id" to mapOf("_eq" to itemId),
                        "profile_id" to mapOf("_eq" to myProfileId)
                    )
                )
                val r3 = productApi.deleteReservationsByProductId(deleteReservationsBody, bearer)
                if (!r3.isSuccessful) throw Exception("No se pudieron borrar las reservas")
            }

            // 6) Finalmente, borrar el producto
            val r4 = productApi.deleteProduct(itemId, bearer)
            if (!r4.isSuccessful) throw Exception("Error al eliminar producto: ${r4.errorBody()?.string()}")

        } catch (e: Exception) {
            Log.e("ProductRepository", "Error en deleteProduct: ${e.message}")
            throw e
        }
    }



    override suspend fun getItemStatuses(): List<ItemStatus> {
        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val response = productApi.getItemStatuses(bearerToken)
        return response.data
    }

    override suspend fun getCategories(): List<Category> {
        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val response = productApi.getCategories(bearerToken)
        return response.data
    }


    override suspend fun getProduct(itemId: Int): ProductData {
        val token = LocalDataSourceProvider.get().load("accesstoken").first()
        val bearerToken = "Bearer $token"
        val response = productApi.getProduct(itemId, bearerToken)

        return response.data
    }

}