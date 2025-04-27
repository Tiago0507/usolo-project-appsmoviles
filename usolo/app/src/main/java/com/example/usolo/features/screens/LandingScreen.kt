package com.example.usolo.features.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LandingScreen(loginController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = listOf(Color(0xFFFF5722), Color(0xFFFF7043))),
                shape = RoundedCornerShape(30.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Usolo", style = MaterialTheme.typography.headlineLarge, color = Color.White)
                Text("Alquila sin\ncomplicaciones", color = Color.White, fontSize = 18.sp, textAlign = TextAlign.Center)
            }

            // Aquí agregas las imágenes
            Row(horizontalArrangement = Arrangement.Center) {
                //Image(painter = painterResource(id = R.drawable.personaje1), contentDescription = null)
                //Image(painter = painterResource(id = R.drawable.personaje2), contentDescription = null)
            }

            Button(
               // onClick = { loginController.navigate("login") },
                onClick = { loginController.navigate("login") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text("¡Comencemos!", color = Color(0xFFFF5722), fontWeight = FontWeight.Bold)
            }
        }
    }
}