package com.projectAnya.stunthink.presentation.screen.main

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import com.projectAnya.stunthink.presentation.navigation.start.StartViewModel
import com.projectAnya.stunthink.presentation.screen.main.camera.CameraScreen
import com.projectAnya.stunthink.presentation.screen.main.education.EducationScreen
import com.projectAnya.stunthink.presentation.screen.main.home.HomeScreen
import com.projectAnya.stunthink.presentation.screen.main.profile.ProfileScreen
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    startViewModel: StartViewModel = hiltViewModel()
) {
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

    LaunchedEffect(key1 = userToken) {
        userToken?.let { token ->
            mainViewModel.getEducationList(token)
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
                        NavigationBarItem(
                            selected = mainViewModel.selectedMenu == index,
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
                                    imageVector = item.icon,
                                    contentDescription = "${item.name} Icon",
                                )
                            }
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
                    1 ->
                        if (permissionState.allPermissionsGranted){
                            CameraScreen(
                                navController = navController,
                                snackbarHostState = snackbarHostState
                            )
                        } else {
                            LaunchedEffect(key1 = permissionState) {
                                permissionState.launchMultiplePermissionRequest()
                            }
                            mainViewModel.changeSelectedMenu(1)
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