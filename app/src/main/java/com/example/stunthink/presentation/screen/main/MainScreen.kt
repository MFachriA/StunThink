package com.example.stunthink.presentation.screen.main

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.School
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stunthink.R
import com.example.stunthink.domain.model.BottomNavItem
import com.example.stunthink.presentation.navigation.ScreenRoute
import com.example.stunthink.presentation.screen.main.camera.CameraScreen
import com.example.stunthink.presentation.screen.main.education.EducationScreen
import com.example.stunthink.presentation.screen.main.home.HomeScreen
import com.example.stunthink.presentation.screen.main.profile.ProfileScreen
import com.example.stunthink.presentation.ui.theme.StunThinkTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
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

    StunThinkTheme {
        var selectedItem by remember { mutableStateOf(0) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.home_title)) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                    mainViewModel.deleteUserToken()
                                },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "back"
                                )
                            }
                        )
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                            },
                            label = {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "${item.name} Icon",
                                )
                            }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selectedItem) {
                    0 -> HomeScreen(navController)
                    1 ->
                        if (permissionState.allPermissionsGranted){
                            CameraScreen(navController)
                        } else {
                            LaunchedEffect(key1 = permissionState) {
                                permissionState.launchMultiplePermissionRequest()
                            }
                            selectedItem = 1
                        }

                    2 -> EducationScreen(navController)
                    3 -> ProfileScreen(navController)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        navController = rememberNavController()
    )
}

val bottomNavItems = listOf(
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