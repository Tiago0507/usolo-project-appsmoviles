package com.example.usolo.features.rental_registration.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.usolo.features.rental_registration.data.dto.RentalItemDTO
import com.example.usolo.features.rental_registration.ui.components.AddProductCard
import com.example.usolo.features.rental_registration.ui.components.BaseProductCard
import com.example.usolo.features.rental_registration.ui.components.RentalProductCard
import com.example.usolo.features.rental_registration.ui.components.TopBarSimple
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController


@Composable
fun RentalRegistrationScreen(
    navController: NavController,
    publishedProducts: List<RentalItemDTO>,
    rentedProducts: List<RentalItemDTO>,
    onCreateProductClick: () -> Unit,
    onCreateRentalClick: () -> Unit
) {
    Scaffold(
        topBar = { TopBarSimple(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
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

                items(publishedProducts) { product ->
                    BaseProductCard(rentalItemDTO = product)
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
                    RentalProductCard(onClick = {
                        navController.navigate("menu")
                    })
                }

                items(rentedProducts) { product ->
                    BaseProductCard(rentalItemDTO = product)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RentalRegistrationScreenPreview() {
    val sampleProducts = listOf(
        RentalItemDTO(
            title = "Silla Gamer",
            pricePerDay = "$30.000 por día",
            imageRes = com.example.usolo.R.drawable.silla_gamer
        ),
        RentalItemDTO(
            title = "Cámara GoPro",
            pricePerDay = "$25.000 por día",
            imageRes = com.example.usolo.R.drawable.silla_gamer
        )
    )

    RentalRegistrationScreen(
        navController = rememberNavController(),
        publishedProducts = sampleProducts,
        rentedProducts = sampleProducts,
        onCreateProductClick = {},
        onCreateRentalClick = {}
    )
}

