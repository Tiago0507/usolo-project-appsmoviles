package com.example.usolo.features.menu.ui.screens


import androidx.compose.foundation.layout.*
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
import androidx.navigation.compose.rememberNavController
import com.example.usolo.features.auth.ui.viewmodel.AUTH_STATE
import com.example.usolo.features.auth.ui.viewmodel.AuthViewModel
import com.example.usolo.features.auth.ui.viewmodel.FakeAuthViewModel
import com.example.usolo.features.auth.ui.viewmodel.IDLE_AUTH_STATE
import com.example.usolo.features.auth.ui.viewmodel.NO_AUTH_STATE
import com.example.usolo.features.menu.ui.components.TopBar
import com.example.usolo.features.menu.ui.components.BottomNavigationBar
import com.example.usolo.features.menu.ui.components.TitleSection
import com.example.usolo.features.menu.ui.components.SearchBar
import com.example.usolo.features.menu.ui.components.CategoryRow
import com.example.usolo.features.menu.ui.components.ProductList
import com.example.usolo.features.menu.ui.components.UserProfileSection


@Composable
fun MainMenu(loginController: NavController, viewModel: AuthViewModel = viewModel()) {

    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAuthStatus()
    }

    when (authState.state) {
        NO_AUTH_STATE -> {

            loginController.navigate("login") {
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
                topBar = { TopBar(loginController = loginController)},
                bottomBar = { BottomNavigationBar() }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(26.dp))
                    CategoryRow()
                    Spacer(modifier = Modifier.height(16.dp))
                    ProductList()
                    Spacer(modifier = Modifier.height(16.dp))
                    // UserProfileSection()
                }
            }
        }
    }
}


