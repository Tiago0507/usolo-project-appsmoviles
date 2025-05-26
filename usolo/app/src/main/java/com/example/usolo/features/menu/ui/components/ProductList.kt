package com.example.usolo.features.menu.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import com.example.usolo.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.usolo.features.menu.data.model.Product
import com.example.usolo.features.menu.data.model.ProductData
import com.example.usolo.features.menu.data.model.ProductWithUser
import coil.compose.rememberAsyncImagePainter
import com.example.usolo.features.menu.ui.viewmodel.ProductListViewModel
import com.example.usolo.features.products.data.dto.ProductData

@Composable
fun ProductList(navController: NavController, viewModel: ProductListViewModel = viewModel()) {
    val products = viewModel.products
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    Column {
        when {
            error != null -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error al cargar productos",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = error,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.retryLoading() }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            products.isEmpty() -> {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay productos disponibles",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            else -> {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {                    items(products) { product ->
                        ProductCard(product = product){
                            val productId = product.product.id.toString()
                            Log.e("ProductList", "Navegando a producto con ID: $productId")
                            navController.navigate("detail/$productId")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductCard(product: ProductWithUser, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(300.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val imageUrl = product.userPhoto ?: ""

            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = product.title,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.product.title,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Text(
                text = "$${product.product.price_per_day} por día",
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            UserProfileSection(product.userName,product.userPhoto)
        }
    }
}