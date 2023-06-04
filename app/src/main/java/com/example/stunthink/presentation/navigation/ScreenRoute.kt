package com.example.stunthink.presentation.navigation

sealed class ScreenRoute(val route: String) {
    object Start: ScreenRoute(route = "start_screen")
    object Welcome: ScreenRoute(route = "welcome_screen")
    object Register: ScreenRoute(route = "register_screen")
    object Login: ScreenRoute(route = "login_screen")
    object Home: ScreenRoute(route = "home_screen")
}