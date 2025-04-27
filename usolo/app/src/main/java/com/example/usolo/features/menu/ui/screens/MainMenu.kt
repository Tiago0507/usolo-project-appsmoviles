package com.example.usolo.features.menu.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.usolo.features.menu.ui.components.TopBar
import com.example.usolo.features.menu.ui.components.BottomNavigationBar
import com.example.usolo.features.menu.ui.components.TitleSection
import com.example.usolo.features.menu.ui.components.SearchBar
import com.example.usolo.features.menu.ui.components.CategoryRow
import com.example.usolo.features.menu.ui.components.ProductList
import com.example.usolo.features.menu.ui.components.UserProfileSection


@Composable
fun MainMenu(loginController: NavController) {
    val navController = rememberNavController()

    Scaffold(

        topBar = {
            TopBar() },
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
