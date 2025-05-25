package com.example.usolo.features.rental_registration.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.usolo.features.rental_registration.ui.components.*
import com.example.usolo.features.rental_registration.ui.viewmodel.RentalRegistrationViewModel

@Composable
fun RentalRegistrationScreen(
    navController: NavController,
    userId: String,
    onCreateProductClick: () -> Unit,
    onCreateRentalClick: () -> Unit,
    viewModel: RentalRegistrationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        if (userId.isEmpty()) {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        } else {
            viewModel.loadItems(userId)
        }
    }


    Scaffold(
        topBar = { TopBarSimple(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 16.dp)
                    )
                }

                uiState.error != null -> {
                    Text(
                        text = uiState.error!!,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    ProductListHorizontal(
                        publishedItems = uiState.publishedItems,
                        rentedItems = uiState.rentedItems,
                        onCreateProductClick = onCreateProductClick,
                        onCreateRentalClick = onCreateRentalClick
                    )
                }
            }
        }
    }
}