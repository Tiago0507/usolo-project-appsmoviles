package com.example.usolo.features.profile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.usolo.features.auth.data.sources.local.LocalDataSourceProvider
import com.example.usolo.features.auth.ui.viewmodel.AuthViewModel
import com.example.usolo.features.profile.ui.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val uiState by profileViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .verticalScroll(rememberScrollState())
    ) {
        // Header con gradiente
        HeaderSection(
            userProfile = uiState.userProfile,
            onSettingsClick = { navController.navigate("settings") },
            onRefreshClick = { profileViewModel.refreshProfile() }
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFFF5722))
                }
            }

            uiState.error != null -> {
                ErrorSection(
                    error = uiState.error!!,
                    onRetry = { profileViewModel.refreshProfile() }
                )
            }

            uiState.userProfile != null -> {
                ProfileContent(
                    userProfile = uiState.userProfile!!,
                    onLogoutClick = {
                        authViewModel.logout()
                        navController.navigate("landing") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HeaderSection(
    userProfile: com.example.usolo.features.profile.domain.model.UserProfile?,
    onSettingsClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    val context = LocalContext.current
    val token = remember {
        runBlocking {
            LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF5722),
                        Color(0xFFFF8A65)
                    )
                )
            )
    ) {
        TopAppBar(
            title = {
                Text(
                    "Mi Perfil",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                IconButton(onClick = onRefreshClick) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Actualizar",
                        tint = Color.White
                    )
                }
                IconButton(onClick = onSettingsClick) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "Configuración",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )

        // Avatar del usuario
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
                .align(Alignment.BottomCenter)
                .offset(y = 50.dp),
            contentAlignment = Alignment.Center
        ) {
            if (userProfile?.profilePhoto != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("http://10.0.2.2:8055/assets/${userProfile.profilePhoto}")
                        .addHeader("Authorization", "Bearer $token")
                        .crossfade(true)
                        .build(),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Avatar",
                    modifier = Modifier.size(60.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun ProfileContent(
    userProfile: com.example.usolo.features.profile.domain.model.UserProfile,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Información básica del usuario
        UserInfoCard(userProfile = userProfile)

        Spacer(modifier = Modifier.height(16.dp))

        // Estadísticas del usuario
        StatsSection(userProfile = userProfile)

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones de perfil
        ProfileOptionsSection(onLogoutClick = onLogoutClick)
    }
}

@Composable
private fun UserInfoCard(userProfile: com.example.usolo.features.profile.domain.model.UserProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Usuario #${userProfile.id}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )

            Text(
                text = "Miembro de USOLO",
                fontSize = 16.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(top = 4.dp)
            )

            if (userProfile.address.isNotEmpty()) {
                Row(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Dirección",
                        tint = Color(0xFFFF5722),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = userProfile.address,
                        fontSize = 14.sp,
                        color = Color(0xFF757575)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatsSection(userProfile: com.example.usolo.features.profile.domain.model.UserProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Mis Estadísticas",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    icon = Icons.Default.Store,
                    value = userProfile.publishedItemsCount.toString(),
                    label = "Publicados",
                    color = Color(0xFFFF5722)
                )

                StatItem(
                    icon = Icons.Default.History,
                    value = userProfile.completedRentalsCount.toString(),
                    label = "Completados",
                    color = Color(0xFF4CAF50)
                )

                StatItem(
                    icon = Icons.Default.Star,
                    value = "4.8",
                    label = "Calificación",
                    color = Color(0xFFFFC107)
                )
            }
        }
    }
}

@Composable
private fun StatItem(
    icon: ImageVector,
    value: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(32.dp)
        )

        Text(
            text = value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A1A),
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF757575)
        )
    }
}

@Composable
private fun ProfileOptionsSection(onLogoutClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            ProfileOption(
                icon = Icons.Default.Help,
                title = "Ayuda y Soporte",
                subtitle = "¿Necesitas ayuda? Contáctanos",
                onClick = { /* TODO: Implementar ayuda */ }
            )

            ProfileOption(
                icon = Icons.Default.Security,
                title = "Privacidad",
                subtitle = "Configurar privacidad de cuenta",
                onClick = { /* TODO: Implementar privacidad */ }
            )
        }
    }
}

@Composable
private fun ProfileOption(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isDestructive) Color(0xFFFFEBEE) else Color.Transparent
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isDestructive) Color(0xFFD32F2F) else Color(0xFFFF5722),
                modifier = Modifier.size(24.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDestructive) Color(0xFFD32F2F) else Color(0xFF1A1A1A)
                )

                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color(0xFF757575)
                )
            }

            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Ir",
                tint = Color(0xFF757575),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun ErrorSection(
    error: String,
    onRetry: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.Error,
                contentDescription = "Error",
                tint = Color(0xFFD32F2F),
                modifier = Modifier.size(48.dp)
            )

            Text(
                text = "Error al cargar perfil",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = error,
                fontSize = 14.sp,
                color = Color(0xFF757575),
                modifier = Modifier.padding(top = 8.dp)
            )

            Button(
                onClick = onRetry,
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722))
            ) {
                Text("Reintentar", color = Color.White)
            }
        }
    }
}