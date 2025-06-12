package com.example.usolo.features.products.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val isCreatingReview by viewModel.isCreatingReview.collectAsState()

    var showCreateReviewDialog by remember { mutableStateOf(false) }

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
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    CreateReviewSection(
                        onCreateReview = { showCreateReviewDialog = true },
                        isLoading = isCreatingReview
                    )
                }
            }
        }
    }
    // Dialog para crear review
    if (showCreateReviewDialog) {
        CreateReviewDialog(
            onDismiss = { showCreateReviewDialog = false },
            onCreateReview = { rating, comment ->
                viewModel.createReview(rating, comment)
                showCreateReviewDialog = false
            },
            isLoading = isCreatingReview
        )
    }
}
@Composable
fun CreateReviewSection(
    onCreateReview: () -> Unit,
    isLoading: Boolean
) {
    Column {
        Text(
            "Escribir una reseña",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onCreateReview,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text("Escribir reseña")
        }
    }
}

@Composable
fun ProductInfoSection(product: ProductData) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            val imageUrl = "http://10.0.2.2:8055/assets/${product.photo}"
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = imageUrl)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = product.title,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.AttachMoney, contentDescription = null, tint = Color(0xFF4CAF50))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("$${product.price_per_day}/día", fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}


@Composable
fun ReviewItem(review: ReviewData, userName: String) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(userName, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(4.dp))
        RatingBar(rating = review.rating)
        Spacer(modifier = Modifier.height(4.dp))
        Text(review.comment, fontSize = 14.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReviewDialog(
    onDismiss: () -> Unit,
    onCreateReview: (Float, String) -> Unit,
    isLoading: Boolean
) {
    var rating by remember { mutableFloatStateOf(0f) }
    var comment by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Escribir una reseña") },
        text = {
            Column {
                Text("Tu valoración")
                Spacer(modifier = Modifier.height(8.dp))
                InteractiveRatingBar(
                    rating = rating,
                    onRatingChanged = { rating = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Tu opinión")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    placeholder = { Text("Escribe una reseña para el producto") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (rating > 0 && comment.isNotBlank()) {
                        onCreateReview(rating, comment)
                    }
                },
                enabled = !isLoading && rating > 0 && comment.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else {
                    Text("Enviar reseña")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun InteractiveRatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    maxRating: Int = 5
) {
    Row {
        for (i in 1..maxRating) {
            val isFilled = rating >= i
            Icon(
                imageVector = if (isFilled) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = null,
                tint = if (isFilled) Color.Yellow else Color.Gray,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onRatingChanged(i.toFloat()) }
            )
        }
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
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}


