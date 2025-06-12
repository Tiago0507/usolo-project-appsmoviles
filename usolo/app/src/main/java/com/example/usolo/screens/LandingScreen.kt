package com.example.usolo.screens
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
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

    // Rebotar imagen del producto
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color1 by infiniteTransition.animateColor(
        initialValue = Color(0xFFF83000), // rojo fuego
        targetValue = Color(0xFFA64A19),  // marrón anaranjado
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

// Color 2: rojo oscuro a naranja encendido
    val color2 by infiniteTransition.animateColor(
        initialValue = Color(0xFFBF360C), // rojo oscuro
        targetValue = Color(0xFFFF5722),  // naranja fuerte
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Cambio automático de imagen cada 3 segundos
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(color1, color2))
            )
            .padding(horizontal = 32.dp, vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Eslogan
            Image(
                painter = painterResource(id = R.drawable.eslogan_image),
                contentDescription = "Eslogan",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Producto animado
            Image(
                painter = painterResource(id = products[currentIndex]),
                contentDescription = "Producto",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .scale(scale)
                    .offset(y = (-40).dp),
                contentScale = ContentScale.Fit
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                products.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .padding(horizontal = 4.dp)
                            .width(if (index == currentIndex) 24.dp else 8.dp)
                            .background(
                                color = if (index == currentIndex) Color.White else Color(0xFFBDBDBD),
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón
            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .offset(y = (-40).dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
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
