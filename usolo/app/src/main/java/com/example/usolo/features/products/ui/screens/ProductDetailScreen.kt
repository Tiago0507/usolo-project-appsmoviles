package com.example.usolo.features.products.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.usolo.features.products.data.dto.ProductData
import com.example.usolo.features.products.data.dto.ReviewData
import com.example.usolo.features.products.ui.viewmodel.ProductDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: Int
) {
    val viewModel: ProductDetailViewModel = viewModel { ProductDetailViewModel(productId) }
    val product by viewModel.product.collectAsState()
    val reviews = viewModel.reviews
    val userNames = viewModel.userNames

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.title ?: "Product Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (product == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    ProductInfoSection(product!!)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* Navigate to reservation screen */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Reservar")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Reviews", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (reviews.isEmpty()) {
                    item {
                        Text("No reviews yet for this product.")
                    }
                } else {
                    itemsIndexed(reviews) { index, review ->
                        ReviewItem(
                            review = review,
                            userName = userNames.getOrElse(index) { "Cargando..." }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun ProductInfoSection(product: ProductData) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        val imageUrl = "http://10.0.2.2:8055/assets/${product.photo}"
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = imageUrl)
                    .apply {
                        crossfade(true)
                        // Add error/placeholder if needed
                    }
                    .build()
            ),
            contentDescription = product.title,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 16.dp),
            contentScale = ContentScale.Crop
        )
        Column {
            Text(
                product.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Precio por día: $${product.price_per_day}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ReviewItem(review: ReviewData, userName: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(userName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        RatingBar(rating = review.rating)
        Text(review.comment, fontSize = 14.sp)
    }
}

@Composable
fun RatingBar(rating: Float, maxRating: Int = 5) {
    Row {
        for (i in 1..maxRating) {
            val icon = when {
                rating >= i -> Icons.Filled.Star
                rating >= i - 0.5f -> Icons.Filled.StarHalf
                else -> Icons.Filled.StarBorder
            }
            Icon(
                imageVector = icon,
                contentDescription = null, // Decorative
                tint = Color.Yellow,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

