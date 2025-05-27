package com.example.usolo.features.postobject.data.sources


import com.example.usolo.features.postobject.domain.model.RentProduct
import com.example.usolo.features.postobject.domain.model.remote.UploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostObjectService {

    @POST("items/item")
    suspend fun createRentProduct(
        @Body product: RentProduct,
        @Header("Authorization") token: String
    ): Response<RentProduct>

    @Multipart
    @POST("files")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Response<UploadResponse>
}
