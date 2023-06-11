package com.example.stunthink.presentation.screen.monitoring.child.main.nutrition

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.example.stunthink.presentation.ui.theme.StunThinkTheme

@Composable
fun ChildNutritionScreen(
    navController: NavController,
    id: String,
    mainViewModel: ChildMonitoringMainViewModel = hiltViewModel(),
    childNutritionViewModel: ChildNutritionViewModel = hiltViewModel()
    ) {
    StunThinkTheme {
        val context = LocalContext.current
        LaunchedEffect(key1 = context) {
            childNutritionViewModel.userTokenFlow.collect { token ->
                if (!token.isNullOrEmpty()) {
                    childNutritionViewModel.getNutritions(id, token)
                }
            }
        }

        val state = childNutritionViewModel.state.value

        Box(
            modifier = Modifier
                .fillMaxSize()
                .draggable(
                    state = mainViewModel.dragState.value!!,
                    orientation = Orientation.Horizontal,
                    onDragStarted = { },
                    onDragStopped = {
                        mainViewModel.updateTabIndexBasedOnSwipe()
                    })
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = state.nutritions, itemContent = { child ->
                    Text(text = child.namaMakanan)
                })
            }
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center)
                )
            }
        }
    }
}