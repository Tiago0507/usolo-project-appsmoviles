package com.example.usolo.features.products.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.usolo.features.products.data.dto.ProductHelper
import com.example.usolo.features.products.data.dto.ReviewData
import com.example.usolo.features.products.ui.viewmodel.ProductDetailViewModel
import com.example.usolo.features.utils.UserHelper.getCurrentUserProfileId

val PrimaryColor = Color(0xFFF83000)
val AccentColor = Color(0xFFFF5722)
val StarColor = Color(0xFFFFC107)
val SuccessColor = Color(0xFF4CAF50)

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ProductDetailScreen(navController: NavController, productId: Int) {
    val context = LocalContext.current
    val viewModel: ProductDetailViewModel = viewModel { ProductDetailViewModel(productId) }
    val product by viewModel.product.collectAsState()
    val reviews = viewModel.reviews
    val userNames = viewModel.userNames
    val isCreatingReview by viewModel.isCreatingReview.collectAsState()

    var showCreateReviewDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(product?.title ?: "Detalles del producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (product == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)
            ) {
                item {
                    ProductInfoSection(product!!)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {

                            // Verificar que no sea su propio producto
                            val currentProfileId = getCurrentUserProfileId()
                            if (product!!.profile_id == currentProfileId) {
                                // Mostrar toast de error

                                Toast.makeText(context, "No puedes reservar tu propio producto", Toast.LENGTH_SHORT).show()
                            } else {
                                // Guardar producto temporalmente y navegar
                                ProductHelper.setCurrentProduct(product!!)
                                navController.navigate("payment/${product!!.id}")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
                    ) {
                        Text("Reservar", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Reseñas", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (reviews.isEmpty()) {
                    item { Text("Este producto aún no tiene reseñas.") }
                } else {
                    itemsIndexed(reviews) { index, review ->
                        ReviewItem(review, userNames.getOrElse(index) { "Cargando..." })
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
fun ProductInfoSection(product: ProductData) {
    val imageUrl = "http://10.0.2.2:8055/assets/${product.photo}"
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(imageUrl).crossfade(true).build()),
                contentDescription = product.title,
                modifier = Modifier.size(100.dp).padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(product.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(product.description, fontWeight = FontWeight.Normal, fontSize = 15.sp)

                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.AttachMoney, contentDescription = null, tint = SuccessColor)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("$${product.price_per_day}/día", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun CreateReviewSection(onCreateReview: () -> Unit, isLoading: Boolean) {
    Column {
        Text("Escribir una reseña", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onCreateReview,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = AccentColor)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text("Escribir reseña", color = Color.White)
        }
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
                InteractiveRatingBar(rating = rating, onRatingChanged = { rating = it })
                Spacer(modifier = Modifier.height(16.dp))
                Text("Tu opinión")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    placeholder = { Text("Escribe una reseña para el producto") },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    maxLines = 4
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (rating > 0 && comment.isNotBlank()) onCreateReview(rating, comment)
                },
                enabled = !isLoading && rating > 0 && comment.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor)
            ) {
                if (isLoading) CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White)
                else Text("Enviar reseña", color = Color.White)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

@Composable
fun InteractiveRatingBar(rating: Float, onRatingChanged: (Float) -> Unit, maxRating: Int = 5) {
    Row {
        for (i in 1..maxRating) {
            val isFilled = rating >= i
            Icon(
                imageVector = if (isFilled) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = null,
                tint = if (isFilled) StarColor else Color.Gray,
                modifier = Modifier.size(32.dp).clickable { onRatingChanged(i.toFloat()) }
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
            Icon(icon, contentDescription = null, tint = StarColor, modifier = Modifier.size(18.dp))
        }
    }
}
