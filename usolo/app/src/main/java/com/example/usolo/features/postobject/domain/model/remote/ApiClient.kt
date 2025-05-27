package com.example.usolo.features.postobject.domain.model.remote

import com.example.usolo.features.postobject.data.repository.sources.DirectusApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:8055/"

    val api: DirectusApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DirectusApi::class.java)
    }
}
