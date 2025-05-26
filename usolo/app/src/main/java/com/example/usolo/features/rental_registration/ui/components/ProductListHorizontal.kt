package com.example.usolo.features.rental_registration.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.usolo.features.rental_registration.domain.model.RentalItem
import com.example.usolo.features.auth.data.repository.AuthRepository

@Composable
fun ProductListHorizontal(
    publishedItems: List<RentalItem>,
    rentedItems: List<RentalItem>,
    onCreateProductClick: () -> Unit,
    onCreateRentalClick: () -> Unit
) {
    val token by produceState(initialValue = "") {
        value = AuthRepository().getAccessToken() ?: ""
    }

    Column {
        // Sección: Mis artículos publicados
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
                BaseProductCard(rentalItem = product, token = token)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sección: Mis alquileres
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
            item {
                RentalProductCard(onClick = onCreateRentalClick)
            }

            items(rentedItems) { product ->
                BaseProductCard(rentalItem = product, token = token)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
