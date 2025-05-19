package com.example.usolo.features.landlord.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.usolo.features.menu.data.model.Product
import com.example.usolo.features.menu.ui.components.ProductCard
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.tooling.preview.Preview
import android.R as AndroidR

@Composable
fun ProductListHorizontal(
    products: List<Product>,
    onAddProductClick: () -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Primero: el card de agregar producto
        item {
            AddProductCard(onClick = onAddProductClick)
        }

        // Luego: los cards de productos reales
        items(products) { product ->
            ProductCard(product = product)
        }
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Primero: el card de alquilar producto
        item {
            RentalProductCard(onClick = onAddProductClick)
        }

        // Luego: los cards de productos reales
        items(products) { product ->
            ProductCard(product = product)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListHorizontalPreview() {
    val sampleProducts = listOf(
        Product("Silla", "$5/día", AndroidR.drawable.ic_menu_gallery),
        Product("Mesa", "$10/día", AndroidR.drawable.ic_menu_gallery),
        Product("Parlante", "$8/día", AndroidR.drawable.ic_menu_gallery)
    )

    ProductListHorizontal(
        products = sampleProducts,
        onAddProductClick = {}
    )
}
