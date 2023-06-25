package com.projectAnya.stunthink.presentation.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.School
import androidx.compose.ui.graphics.vector.ImageVector
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

object BottomNavItems {
    val items = listOf(
        BottomNavItem(
            name = "Beranda",
            route = ScreenRoute.Home.route,
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = "Kamera",
            route = ScreenRoute.Camera.route,
            icon = Icons.Rounded.PhotoCamera,
        ),
        BottomNavItem(
            name = "Edukasi",
            route = ScreenRoute.Education.route,
            icon = Icons.Rounded.School,
        ),
        BottomNavItem(
            name = "Akun",
            route = ScreenRoute.Profile.route,
            icon = Icons.Rounded.AccountCircle,
        )
    )
}