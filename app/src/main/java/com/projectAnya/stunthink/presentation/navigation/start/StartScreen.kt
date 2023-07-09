package com.projectAnya.stunthink.presentation.navigation.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme

@Composable
fun StartScreen(
    navController: NavController,
    viewModel: StartViewModel = hiltViewModel()
) {
    val userTokenState: State<String?> = viewModel.userTokenState.collectAsState()
    val userToken: String? by userTokenState

    StunThinkTheme {
        LaunchedEffect(key1 = userToken) {
            if (userToken.isNullOrEmpty()) {
                navController.navigate(route = ScreenRoute.Welcome.route) {
                    popUpTo(ScreenRoute.Start.route) { inclusive = true }
                }
            } else {
                navController.navigate(route = ScreenRoute.Main.route) {
                    popUpTo(ScreenRoute.Welcome.route) { inclusive = true }
                }
            }
        }
    }
}