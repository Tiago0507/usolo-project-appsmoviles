package com.example.usolo.features.postobject.domain.model.remote

data class UploadResponse(
    val data: UploadedFile
)

data class UploadedFile(
    val id: String
)
