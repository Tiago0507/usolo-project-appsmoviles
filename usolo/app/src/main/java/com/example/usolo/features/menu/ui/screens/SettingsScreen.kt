package com.example.usolo.features.settings.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.usolo.R
import com.example.usolo.features.auth.ui.viewmodel.AuthViewModel

@Composable
fun SettingsScreen(
    loginController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    val gradientColors = listOf(
        Color(0xFFF83000),
        Color(0xFFFF6600)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = Brush.verticalGradient(colors = gradientColors))
                .padding(start = 32.dp, end = 16.dp, top = 32.dp, bottom = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Ajustes",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    DrawerOption(R.drawable.ic_profile, "Perfil")
                    DrawerOption(R.drawable.ic_orders, "Órdenes")
                    DrawerOption(R.drawable.ic_offers, "Ofertas")
                    DrawerOption(R.drawable.ic_privacy_policy, "Política de privacidad")
                    DrawerOption(R.drawable.ic_security, "Seguridad")
                }

                LogoutTextButton {
                    viewModel.logout()
                    loginController.navigate("landing") {
                        popUpTo("settings") { inclusive = true }
                        popUpTo("menu") { inclusive = true }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerOption(iconResId: Int, title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = title,
                modifier = Modifier
                    .size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Divider(
            color = Color.White.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Composable
fun LogoutTextButton(onClick: () -> Unit) {
    Text(
        text = "Cerrar sesión →",
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .padding(start = 8.dp)
            .clickable { onClick() }
    )
}
