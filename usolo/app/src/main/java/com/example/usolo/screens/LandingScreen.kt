package com.example.usolo.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.usolo.R
import kotlinx.coroutines.delay

@Composable
fun LandingScreen(navController: NavController) {
    val products = listOf(
        R.drawable.prod_1,
        R.drawable.prod_2,
        R.drawable.prod_3,
        R.drawable.prod_4,
        R.drawable.prod_5,
        R.drawable.prod_6,

    )

    var currentIndex by remember { mutableStateOf(0) }
    var forward by remember { mutableStateOf(true) }

    // Animación con rebote
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Cambio automático de imagen
    LaunchedEffect(currentIndex) {
        delay(3000L)
        currentIndex = if (forward) {
            if (currentIndex < products.lastIndex) currentIndex + 1 else {
                forward = false; currentIndex - 1
            }
        } else {
            if (currentIndex > 0) currentIndex - 1 else {
                forward = true; currentIndex + 1
            }
        }
    }

    // Fondo naranja
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF83000))
            .padding(horizontal = 32.dp, vertical = 24.dp)
    ) {
        // Contenido vertical organizado
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp), // menos padding vertical

            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Eslogan
            Image(
                painter = painterResource(id = R.drawable.eslogan_image),
                contentDescription = "Eslogan",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            // Imagen animada del producto
            Image(
                painter = painterResource(id = products[currentIndex]),
                contentDescription = "Producto",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(300.dp)
                    .height(200.dp)
                    .scale(scale)
                    .offset(y = (-50).dp),

                contentScale = ContentScale.Fit
            )

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .offset(y = (-50).dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(Color.White)
            ){
                Text(
                    "¡Comencemos!",
                    color = Color(0xFFFF5722),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp

                )
            }
        }
    }
}
