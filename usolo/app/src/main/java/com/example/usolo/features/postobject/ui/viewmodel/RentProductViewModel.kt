package com.example.usolo.features.postobject.ui.viewmodel
import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usolo.features.auth.data.repository.AuthRepository
import com.example.usolo.features.postobject.data.model.RentProduct
import com.example.usolo.features.postobject.data.model.remote.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody


class RentProductViewModel : ViewModel() {

    private val _publishState = MutableStateFlow<Result<Unit>?>(null)
    val publishState: StateFlow<Result<Unit>?> = _publishState

    private val authRepository = AuthRepository()

    fun publishWithImage(product: RentProduct, resolver: ContentResolver, uri: Uri?) {
        viewModelScope.launch {
            try {
                val token = authRepository.getAccessToken() ?: run {
                    _publishState.value = Result.failure(Exception("Token no disponible"))
                    return@launch
                }

                val photoId = uri?.let {
                    uploadImage(resolver, it, token)
                }

                val finalProduct = product.copy(photo = photoId)
                publish(finalProduct)
            } catch (e: Exception) {
                _publishState.value = Result.failure(e)
            }
        }
    }

    suspend fun uploadImage(resolver: ContentResolver, uri: Uri, token: String): String? {
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

    fun publish(product: RentProduct) {
        viewModelScope.launch {
            try {
                val token = authRepository.getAccessToken() ?: return@launch
                val result = ApiClient.api.createRentProduct(product, "Bearer $token")
                _publishState.value = if (result.isSuccessful) Result.success(Unit)
                else Result.failure(Exception("Error al publicar: ${result.code()}"))
            } catch (e: Exception) {
                _publishState.value = Result.failure(e)
            }
        }
    }
}
