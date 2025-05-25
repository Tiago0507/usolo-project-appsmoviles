package com.example.authclass10.config

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitConfig {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val directusRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8055")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}