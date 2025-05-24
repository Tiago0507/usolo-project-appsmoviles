package com.example.usolo.features.menu.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.usolo.features.menu.data.model.ProductWithUser
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.usolo.features.menu.data.model.ReviewData
import com.example.usolo.features.menu.ui.viewmodel.ProductDetailViewModel

@Composable
fun ProductDetailScreen(
    itemId: String,
    navController: NavController,
    onRentClick: () -> Unit
) {
    // Crear el ViewModel usando la función de composición correctamente
    val viewModel: ProductDetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    
    val product by viewModel.product.collectAsState()
    val status by viewModel.status.collectAsState()
    val reviews by viewModel.reviews.collectAsState()    
    
    // Estado de carga
    val isLoading by viewModel.loading.collectAsState()
    
    // Identificador único para diagnóstico
    val instanceId = remember { System.currentTimeMillis().toString() }
    
    Log.d("ProductDetail", "[$instanceId] Pantalla renderizada con itemId: '$itemId', viewModel: $viewModel")
    
    LaunchedEffect(itemId) {
        Log.d("ProductDetail", "[$instanceId] LaunchedEffect ejecutado para ID: '$itemId'")
        viewModel.loadItemDetails(itemId)
    }
    
    Log.d("ProductDetail", "[$instanceId] Estado actual - producto: ${product != null}, isLoading: $isLoading")
    
    if (product == null) {        
        Log.d("ProductDetail", "[$instanceId] El producto es nulo para ID: '$itemId', isLoading=$isLoading")
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
            
            // Mostrar un mensaje mientras se carga
            if (isLoading) {
                Text(
                    text = "Cargando producto...",
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        return
    } else {
        Log.d("ProductDetail", "[$instanceId] Mostrando producto: ${product?.title}")
    }

    Column(
        modifier = Modifier
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
                Text(product!!.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Estado: ${status?.name ?: "Cargando..."}")
                Text("$${product!!.price_per_day}/día")
            }
            Button(
                onClick = onRentClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
            ) {
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

        // Aquí podrías habilitar el formulario de reseña si lo deseas
    }
}

@Composable
fun ReviewItem(review: ReviewData) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        //Text(review.userName, fontWeight = FontWeight.Bold)
        Row {
            repeat(review.rating) { Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow) }
        }
        Text(review.comment)
    }
}



/*
@Composable
fun ReviewForm(onSubmit: (Int, String) -> Unit) {
    var rating by remember { mutableStateOf(0) }
    var opinion by remember { mutableStateOf("") }

    Column {
        Text("Tu valoración")
        Row {
            for (i in 1..5) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star $i",
                    tint = if (i <= rating) Color.Yellow else Color.Gray,
                    modifier = Modifier
                        .clickable { rating = i }
                        .padding(4.dp)
                )
            }
        }

        Text("Tu opinión")
        OutlinedTextField(
            value = opinion,
            onValueChange = { opinion = it },
            placeholder = { Text("Escribe una reseña para el producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { onSubmit(rating, opinion) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
        ) {
            Text("Enviar reseña")
        }
    }
}
*/



