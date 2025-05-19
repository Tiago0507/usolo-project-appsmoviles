package com.example.usolo.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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

@Composable
fun HomeScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {

    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAuthStatus()
    }

    when(authState.state) {
        NO_AUTH_STATE -> {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
        AUTH_STATE -> {
            Column {
                Box(modifier = Modifier.height(200.dp))
                Text(text = "¡Bienvenido!")
            }
        }
        IDLE_AUTH_STATE -> {
            CircularProgressIndicator()
        }
    }
}