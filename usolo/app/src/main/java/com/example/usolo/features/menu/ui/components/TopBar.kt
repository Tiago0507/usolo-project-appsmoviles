package com.example.usolo.features.menu.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController

@Composable
fun TopBar(
    loginController: NavController,
    onCartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFFF83000), Color(0xFFFF6600))
                )
            )
            .zIndex(1f)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { loginController.navigate("settings") }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            }
            IconButton(onClick = {
                // Puedes controlar esto con una condición si lo necesitas
                onCartClick()
            }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(

            text = "Artículos sin complicaciones",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        SearchBar(modifier = Modifier.fillMaxWidth())
    }
}
