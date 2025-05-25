  package com.example.usolo.features.products.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
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
fun TopBarProducts(loginController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

            .statusBarsPadding()
            .padding(30.dp)
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
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color(0xFFFF6600)
                )
            }
            IconButton(onClick = {
                //loginController.navigate("products")
            }) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color(0xFFFF6600)
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 35.dp)
                .width(360.dp),
            color = Color(0xFFFF6600),
            thickness = 2.dp
        )

        Spacer(modifier = Modifier.height(4.dp))



    }
}
