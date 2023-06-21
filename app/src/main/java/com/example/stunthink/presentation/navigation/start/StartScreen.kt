package com.example.stunthink.presentation.navigation.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stunthink.presentation.navigation.ScreenRoute
import com.example.stunthink.presentation.ui.theme.StunThinkTheme

@Composable
fun StartScreen(
    navController: NavController,
    viewModel: StartViewModel = hiltViewModel()
) {
    val userToken by viewModel.userToken.collectAsState()

    StunThinkTheme {
        LaunchedEffect(key1 = userToken) {
            if (userToken.isNullOrEmpty()) {
                navController.navigate(route = ScreenRoute.Welcome.route) {
                    popUpTo(ScreenRoute.Start.route) { inclusive = true }
                }
            } else {
                navController.navigate(route = ScreenRoute.Main.route) {
                    popUpTo(ScreenRoute.Start.route) { inclusive = true }
                }
            }
        }
    }
}