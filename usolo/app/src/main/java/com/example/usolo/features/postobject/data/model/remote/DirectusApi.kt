package com.example.usolo.features.postobject.data.model.remote


import com.example.usolo.features.postobject.data.model.RentProduct
import com.google.firebase.appdistribution.gradle.models.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DirectusApi {

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
    ): Response<com.example.usolo.features.postobject.data.model.remote.UploadResponse>
}
