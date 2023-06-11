package com.example.stunthink.presentation.screen.monitoring.child.main.nutrition

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.example.stunthink.presentation.ui.theme.StunThinkTheme

@Composable
fun ChildNutritionScreen(
    navController: NavController,
    viewModel: ChildMonitoringMainViewModel = hiltViewModel()
) {
    StunThinkTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .draggable(
                    state = viewModel.dragState.value!!,
                    orientation = Orientation.Horizontal,
                    onDragStarted = {  },
                    onDragStopped = {
                        viewModel.updateTabIndexBasedOnSwipe()
                    })
        ) {
            Text(text = "Nutrition")
        }
    }
}