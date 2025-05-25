package com.example.usolo.features.products.ui.screens

import CreateArticleCard
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.usolo.features.auth.ui.viewmodel.AUTH_STATE
import com.example.usolo.features.auth.ui.viewmodel.AuthViewModel
import com.example.usolo.features.auth.ui.viewmodel.IDLE_AUTH_STATE
import com.example.usolo.features.auth.ui.viewmodel.NO_AUTH_STATE
import com.example.usolo.features.products.ui.components.ProductListOwned
import com.example.usolo.features.products.ui.components.TopBarProducts


@Composable
fun ViewProductsScreen(navController: NavController, viewModel: AuthViewModel = viewModel()){

    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAuthStatus()
    }

    when(authState.state){
        NO_AUTH_STATE -> {
            navController.navigate("login"){
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }

        IDLE_AUTH_STATE -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }

        AUTH_STATE -> {
            Scaffold(
                topBar = { TopBarProducts(loginController = navController) },
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ProductListOwned(navController = navController)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}