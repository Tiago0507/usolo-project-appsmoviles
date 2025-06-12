package com.example.usolo.features.payment_gateway.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.products.data.dto.ProductData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

@Composable
fun ProductInfoCard(product: ProductData) {
    val context = LocalContext.current
    val token = remember {
        runBlocking {
            LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Producto a reservar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF5722)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("http://10.0.2.2:8055/assets/${product.photo}")
                        .addHeader("Authorization", "Bearer $token")
                        .crossfade(true)
                        .build(),
                    contentDescription = product.title,
                    modifier = Modifier
                        .size(80.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = product.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "$${product.price_per_day}/día",
                        fontSize = 16.sp,
                        color = Color(0xFFFF5722),
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = product.description ?: "",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}