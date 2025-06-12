package com.example.usolo.config

import com.example.usolo.features.menu.data.model.UUIDAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfig {

    private val moshi = Moshi.Builder()
        .add(UUIDAdapter()) //
        .add(KotlinJsonAdapterFactory())
        .build()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val directusRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.0.2:8055")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}
