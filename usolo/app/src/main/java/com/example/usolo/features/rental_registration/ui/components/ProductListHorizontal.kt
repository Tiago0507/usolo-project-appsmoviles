package com.example.usolo.features.rental_registration.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.usolo.features.rental_registration.domain.model.RentalItem

@Composable
fun ProductListHorizontal(
    publishedItems: List<RentalItem>,
    rentedItems: List<RentalItem>,
    token: String,
    onCreateProductClick: () -> Unit,
    onCreateRentalClick: () -> Unit,
    onProductClick: (Int) -> Unit
) {
    Column {
        // Sección de artículos publicados
        Text(
            text = "Mis artículos publicados",
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
            color = Color(0xFFFF5722),
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AddProductCard(onClick = onCreateProductClick)
            }

            items(publishedItems) { product ->
                BaseProductCard(
                    rentalItem = product,
                    token = token,
                    onClick = { onProductClick(product.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sección de artículos alquilados
        Text(
            text = "Mis alquileres",
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
            color = Color(0xFFFFA25B),
            fontWeight = FontWeight.Bold
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(rentedItems) { product ->
                RentalProductCard(
                    rentalItem = product,
                    token = token
                    // No pasamos onCardClick para que no sea clickeable
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
