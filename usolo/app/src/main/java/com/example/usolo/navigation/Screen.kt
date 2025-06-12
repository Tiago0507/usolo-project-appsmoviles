package com.example.usolo.navigation
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data object History : Screen()

    @Serializable
    data object Profile : Screen()

    @Serializable
    data object Cart : Screen()
}

data class BottomNavItem(
    val screen: Screen,
    val title: String,
    val iconRes: Int,
    val route: String
)