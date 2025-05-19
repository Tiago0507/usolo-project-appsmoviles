package com.example.usolo.features.menu.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.usolo.features.menu.data.model.ProductWithUser
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ProductDetailScreen(
    product: ProductWithUser,
    viewModel: ProductDetailViewModel = hiltViewModel(),
    onRentClick: () -> Unit
) {
    val status by viewModel.status.collectAsState()
    val reviews by viewModel.reviews.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadStatus(product.product.status_id)
        viewModel.loadReviews(product.product.id)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp)
    ) {
        // Sección del producto
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(product.product.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Alquiler: ${product.product.start_date} - ${product.product.end_date}")
                Text("Estado: ${status?.name ?: "Cargando..."}")
                Text("$${product.product.price_per_day}/día")
            }
            Button(onClick = onRentClick, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))) {
                Text("Alquilar")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Opiniones
        Text("Opiniones sobre este artículo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))

        if (reviews.isEmpty()) {
            Text("Aún no hay reseñas")
        } else {
            reviews.forEach { review ->
                ReviewItem(review)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Escribir reseña
        Text("Escribir una reseña", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        ReviewForm(onSubmit = { rating, opinion ->
            viewModel.submitReview(product.product.id, rating, opinion)
        })
    }
}
