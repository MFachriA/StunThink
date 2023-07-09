package com.projectAnya.stunthink.presentation.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.School
import com.projectAnya.stunthink.domain.model.home.BottomNavItem
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute

object BottomNavItems {
    val items = listOf(
        BottomNavItem(
            name = "Beranda",
            route = ScreenRoute.Home.route,
            icon = Icons.Rounded.Home,
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