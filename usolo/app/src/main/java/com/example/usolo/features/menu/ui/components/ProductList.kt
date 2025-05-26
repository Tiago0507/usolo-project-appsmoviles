package com.example.usolo.features.menu.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.menu.ui.viewmodel.ProductListViewModel
import com.example.usolo.features.products.data.dto.ProductData
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun ProductList(viewModel: ProductListViewModel = viewModel()) {
    val products = viewModel.products
    val userNames = viewModel.userNames

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            val ownerName = userNames[product.profile_id] ?: "Usuario desconocido"
            ProductCard(product = product, userName = ownerName)
        }
    }
}

@Composable
fun ProductCard(product: ProductData, userName: String) {
    val context = LocalContext.current
    val token = rememberToken()
    val imageUrl = "http://10.0.2.2:8055/assets/${product.photo}"

    Log.d("ProductCard", "Image URL: $imageUrl")

    val imageRequest = ImageRequest.Builder(context)
        .data(imageUrl)
        .addHeader("Authorization", "Bearer $token")
        .crossfade(true)
        .build()

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = product.title,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Text(
                text = "$${product.price_per_day}",
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Publicado por $userName",
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

// Helper para obtener el token sincronamente
@Composable
fun rememberToken(): String {
    return runBlocking {
        LocalDataSourceProvider.get().load("accesstoken").firstOrNull() ?: ""
    }
}
