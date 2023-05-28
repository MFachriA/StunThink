package com.example.stunthink.presentation.navigation

sealed class ScreenRoute(val route: String) {
    object Welcome: ScreenRoute(route = "welcome_screen")
    object SignUp: ScreenRoute(route = "sign_up_screen")
    object SignIn: ScreenRoute(route = "sign_in_screen")
}