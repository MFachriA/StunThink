package com.projectAnya.stunthink.presentation.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DesktopWindows
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.School
import com.projectAnya.stunthink.domain.model.home.BottomNavItem
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute

object BottomNavItems {
    val items = listOf(
        BottomNavItem(
            name = "Beranda",
            route = ScreenRoute.Home.route,
            icon_active = Icons.Filled.Home,
            icon_inactive = Icons.Outlined.Home
        ),
        BottomNavItem(
            name = "Monitoring",
            route = ScreenRoute.Education.route,
            icon_active = Icons.Filled.DesktopWindows,
            icon_inactive = Icons.Outlined.DesktopWindows
        ),
        BottomNavItem(
            name = "Edukasi",
            route = ScreenRoute.Education.route,
            icon_active = Icons.Filled.School,
            icon_inactive = Icons.Outlined.School
        ),
        BottomNavItem(
            name = "Akun",
            route = ScreenRoute.Profile.route,
            icon_active = Icons.Filled.AccountCircle,
            icon_inactive = Icons.Outlined.AccountCircle
        )
    )
}