package com.example.usolo.domain.dto

data class FCMMessage(
    val message: FCMData
)

data class FCMData(
    val topic: String,
    val data: Map<String, String>
)

