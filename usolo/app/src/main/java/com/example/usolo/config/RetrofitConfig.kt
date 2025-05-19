package com.example.authclass10.config

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val directusRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8055")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


}