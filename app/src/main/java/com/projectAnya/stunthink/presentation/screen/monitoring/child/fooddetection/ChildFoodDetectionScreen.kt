package com.projectAnya.stunthink.presentation.screen.monitoring.child.fooddetection

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.screen.main.camera.CameraScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme

@Composable
fun ChildFoodDetectionScreen(
    navController: NavController,
    viewModel: ChildMonitoringMainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val childIdState: State<String?> = viewModel.childIdState.collectAsState()
    val childId: String? by childIdState

    LaunchedEffect(context) {
        Toast.makeText(context, childId, Toast.LENGTH_LONG).show()
    }

    StunThinkTheme {
        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = "Tambah Makanan Anak",
                    navigationOnClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    CameraScreen(
                        navController = navController,
                        snackbarHostState = snackbarHostState,

                    )
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailScreenPreview() {
    ChildFoodDetectionScreen(
        navController = rememberNavController()
    )
}