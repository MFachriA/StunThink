package com.example.stunthink.presentation.navigation.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stunthink.presentation.navigation.ScreenRoute
import com.example.stunthink.presentation.ui.theme.StunThinkTheme

@Composable
fun StartScreen(
    navController: NavController,
    homeViewModel: StartViewModel = hiltViewModel()
) {
    StunThinkTheme {
        val context = LocalContext.current
        LaunchedEffect(key1 = context) {
            homeViewModel.userTokenFlow.collect { token ->
                if (token.isNullOrEmpty()) {
                    navController.navigate(route = ScreenRoute.Welcome.route) {
                        popUpTo(ScreenRoute.Start.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(route = ScreenRoute.Home.route) {
                        popUpTo(ScreenRoute.Start.route) { inclusive = true }
                    }
                }
            }
        }
    }
}