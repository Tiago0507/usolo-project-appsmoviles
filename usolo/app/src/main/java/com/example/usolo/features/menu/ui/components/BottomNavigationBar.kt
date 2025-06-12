package com.example.usolo.features.menu.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.usolo.R
import com.example.usolo.navigation.BottomNavItem
import com.example.usolo.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(
            screen = Screen.Home,
            title = "Inicio",
            iconRes = R.drawable.ic_home,
            route = "menu"
        ),
        BottomNavItem(
            screen = Screen.History,
            title = "Historial",
            iconRes = R.drawable.ic_orders,
            route = "history"
        ),
        BottomNavItem(
            screen = Screen.Profile,
            title = "Perfil",
            iconRes = R.drawable.ic_profile,
            route = "profile"
        ),
        BottomNavItem(
            screen = Screen.Cart,
            title = "Alquileres",
            iconRes = R.drawable.ic_cart,
            route = "rental_registration"
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFFFF5722)
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp),
                        tint = if (isSelected) Color(0xFFFF5722) else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) Color(0xFFFF5722) else Color.Gray
                    )
                },
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFF5722),
                    selectedTextColor = Color(0xFFFF5722),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFFFF5722).copy(alpha = 0.1f)
                )
            )
        }
    }
}