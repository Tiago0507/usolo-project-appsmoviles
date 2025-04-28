package com.example.usolo.features.registration.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import com.example.usolo.R
import androidx.compose.ui.text.font.FontWeight




@Composable
fun SignUpScreen(loginController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Usolo", style = MaterialTheme.typography.headlineLarge, color = Color(0xFFFF5722))

        Row(modifier = Modifier.padding(top = 16.dp)) {
            Text("Iniciar sesión", color = Color.Gray)
            Spacer(modifier = Modifier.width(16.dp))
            Text("Registrarse", fontWeight = FontWeight.Bold, color = Color(0xFFFF5722))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { loginController.navigate("email_signup") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Continuar con el correo", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón Facebook
        Button(
            onClick = { /* sin funcionalidad */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2)),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(painterResource(id = R.drawable.facebook), contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Continuar con Facebook", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón Google
        Button(
            onClick = { /* sin funcionalidad */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(painterResource(id = R.drawable.google), contentDescription = null, tint = Color.Unspecified)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Continuar con Google", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón Apple
        Button(
            onClick = { /* sin funcionalidad */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(painterResource(id = R.drawable.apple), contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Continuar con Apple", color = Color.Black)
        }
    }
}
