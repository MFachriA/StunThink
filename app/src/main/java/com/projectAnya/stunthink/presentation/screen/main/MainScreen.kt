package com.projectAnya.stunthink.presentation.screen.main

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.navigation.start.StartViewModel
import com.projectAnya.stunthink.presentation.screen.main.education.EducationScreen
import com.projectAnya.stunthink.presentation.screen.main.home.HomeScreen
import com.projectAnya.stunthink.presentation.screen.main.monitoring.MonitoringScreen
import com.projectAnya.stunthink.presentation.screen.main.profile.ProfileScreen
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.md_theme_light_primary

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    startViewModel: StartViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = mainViewModel.educationState.value

    val bottomNavItems = BottomNavItems.items
    val userTokenState: State<String?> = startViewModel.userTokenState.collectAsState()
    val userToken: String? by userTokenState

    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.CAMERA
        )
    )
    if (!permissionState.allPermissionsGranted){
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state) {
        mainViewModel.validationEvents.collect { event ->
            when (event) {
                is MainViewModel.ValidationEvent.Failed -> {
                    println("Error Message :${event.message}")
                    if (event.message?.contains("401") == true) {
                        mainViewModel.deleteUserToken()
                        navController.navigate(route = ScreenRoute.Welcome.route) {
                            popUpTo(ScreenRoute.Main.route) { inclusive = true }
                        }
                        Toast.makeText(context, "Sesi anda telah habis, silahkan login kembali!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = userToken) {
        userToken?.let { token ->
            mainViewModel.getEducationList(token)
            mainViewModel.getUserDetail(token)
        }
    }

    LaunchedEffect(key1 = context) {
        userToken?.let { token ->
            mainViewModel.getNutritionStatus(token, null)
            mainViewModel.getNutritionStandard(token)
            mainViewModel.getUserDetail(token)
        }
    }

    StunThinkTheme {
        Scaffold(
            topBar = {
                Surface(shadowElevation = 3.dp) {
                    TopAppBar(
                        title = {
                            Image(
                                painter = painterResource(id = R.drawable.stunthink_logo_plain),
                                contentDescription = null
                            )
                        }
                    )
                }
            },
            bottomBar = {
                NavigationBar {
                    bottomNavItems.forEachIndexed { index, item ->
                        val isActive = mainViewModel.selectedMenu == index
                        NavigationBarItem(
                            selected = isActive,
                            onClick = {
                                mainViewModel.changeSelectedMenu(index)
                            },
                            label = {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector =
                                    if (isActive) item.icon_active else item.icon_inactive,
                                    contentDescription = "${item.name} Icon",
                                )
                            },
                            colors =  NavigationBarItemDefaults.colors(
                                selectedIconColor = md_theme_light_primary,
                                selectedTextColor = md_theme_light_primary
                            )
                        )
                    }
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (mainViewModel.selectedMenu) {
                    0 -> HomeScreen(navController)
                    1 -> MonitoringScreen(navController)
                    2 -> EducationScreen(navController)
                    3 -> ProfileScreen(navController)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        navController = rememberNavController()
    )
}