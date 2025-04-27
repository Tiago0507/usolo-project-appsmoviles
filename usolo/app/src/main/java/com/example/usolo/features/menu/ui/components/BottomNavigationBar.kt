package com.example.usolo.features.menu.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.usolo.R

@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        containerColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO: Acción Home */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Inicio",
                    modifier = Modifier.size(25.dp)
                )
            }
            IconButton(onClick = { /* TODO: Acción Favoritos */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorite),
                    contentDescription = "Favoritos",
                    modifier = Modifier.size(25.dp)
                )
            }
            IconButton(onClick = { /* TODO: Acción Perfil */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(25.dp)
                )
            }
            IconButton(onClick = { /* TODO: Acción Carrito */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = "Carrito",
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
}

