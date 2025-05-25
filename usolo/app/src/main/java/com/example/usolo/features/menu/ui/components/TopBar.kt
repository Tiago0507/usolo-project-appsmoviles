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
import androidx.compose.ui.zIndex // Importar zIndex
import androidx.navigation.NavController

@Composable
fun TopBar(loginController: NavController, onCartClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFF83000),
                        Color(0xFFFF6600)
                    )
                )
            )
            .statusBarsPadding()
            .padding(20.dp)
            .zIndex(0f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {

            IconButton(onClick = {
                loginController.navigate("settings")
            },) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            }
            IconButton(onClick = onCartClick) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Artículos sin complicaciones",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 40.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp, bottom = 20.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        SearchBar()

        Spacer(modifier = Modifier.height(8.dp))
    }
}
