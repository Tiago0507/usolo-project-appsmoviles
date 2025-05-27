package com.example.usolo.features.postobject.ui.viewmodel

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.auth.data.repository.AuthRepository
import com.example.usolo.features.auth.data.repository.AuthRepositoryImpl
import com.example.usolo.features.postobject.domain.model.RentProduct
import com.example.usolo.features.postobject.domain.model.remote.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RentProductViewModel : ViewModel() {

    private val _publishState = MutableStateFlow<Result<Unit>?>(null)
    val publishState: StateFlow<Result<Unit>?> = _publishState

    private val authRepository = AuthRepositoryImpl()

    fun publishWithImage(
        title: String,
        description: String,
        price: Double,
        categoryId: Int,
        imageUri: Uri?,
        profileId: Int,
        resolver: ContentResolver
    ) {
        viewModelScope.launch {
            try {
                val token = authRepository.getAccessToken()
                if (token.isNullOrBlank()) {
                    _publishState.value = Result.failure(Exception("Token no disponible"))
                    return@launch
                }

                val photoId = imageUri?.let { uploadImage(it, token, resolver) }

                val product = RentProduct(
                    title = title,
                    description = description,
                    price_per_day = price,
                    category_id = categoryId,
                    availability = true,
                    photo = photoId,
                    profile_id = profileId,
                    status_id = 1
                )

                val response = ApiClient.api.createRentProduct(product, "Bearer $token")
                _publishState.value = if (response.isSuccessful) Result.success(Unit)
                else Result.failure(Exception("Error al publicar: ${response.code()}"))

            } catch (e: Exception) {
                _publishState.value = Result.failure(e)
            }
        }
    }

    private suspend fun uploadImage(
        uri: Uri,
        token: String,
        resolver: ContentResolver
    ): String? {
        return try {
            val inputStream = resolver.openInputStream(uri)
            val bytes = inputStream?.readBytes() ?: return null
            inputStream.close()

            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), bytes)
            val part = MultipartBody.Part.createFormData("file", "upload.jpg", requestFile)

            val response = ApiClient.api.uploadFile(part, "Bearer $token")
            if (response.isSuccessful) {
                response.body()?.data?.id
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
