package com.example.usolo.features.rental_registration.ui.components

import BaseProductCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.usolo.features.menu.data.model.Product
import com.example.usolo.features.menu.ui.components.ProductCard
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.usolo.features.rental_registration.domain.model.RentalItem
import android.R as AndroidR

@Composable
fun ProductListHorizontal(
    publishedItems: List<RentalItem>,
    rentedItems: List<RentalItem>,
    onCreateProductClick: () -> Unit,
    onCreateRentalClick: () -> Unit
) {
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
                BaseProductCard(rentalItem = product)
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
                BaseProductCard(rentalItem = product)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

