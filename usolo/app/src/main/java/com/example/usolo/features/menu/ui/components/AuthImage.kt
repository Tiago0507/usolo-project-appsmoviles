package com.example.usolo.features.menu.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AuthenticatedImage(
    url: String,
    token: String,
    contentDescription: String?,
    modifier: Modifier
) {
    val context: Context = LocalContext.current

    val request = ImageRequest.Builder(context)
        .data(url)
        .addHeader("Authorization", "Bearer $token")
        .crossfade(true)
        .build()

    AsyncImage(
        model = request,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}
