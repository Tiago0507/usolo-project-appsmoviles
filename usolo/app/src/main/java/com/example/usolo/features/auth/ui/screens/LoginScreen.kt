package com.example.usolo.features.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.usolo.features.auth.ui.viewmodel.AUTH_STATE
import com.example.usolo.features.auth.ui.viewmodel.AuthViewModel


@Composable
fun LoginScreen(viewModel: AuthViewModel = viewModel(), loginController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Usolo", style = MaterialTheme.typography.headlineLarge, color = Color(0xFFFF5722))

        Row(modifier = Modifier.padding(top = 16.dp)) {
            Text("Iniciar sesión", fontWeight = FontWeight.Bold, color = Color(0xFFFF5722))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Registrarse",
                color = Color.Gray,
                modifier = Modifier.clickable {
                    loginController.navigate("signup")
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            "¿Olvidó su contraseña?",
            color = Color(0xFFFF5722),
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        val authState by viewModel.authState.collectAsState()

        if (authState.state == AUTH_STATE) {
            loginController.navigate("home") {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }

        Button(
            onClick = {
                viewModel.login(email, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
        ) {
            Text("Iniciar sesión", color = Color.White)
        }
    }

}