package com.example.authclass10.config

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfig {

    // Configurar Moshi (de nueva-rama)
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Configurar cliente HTTP con logging (de dev)
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit usando Moshi y el cliente configurado
    val directusRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8055")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}
