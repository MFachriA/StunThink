package com.example.stunthink.presentation.screen.monitoring.child.main.nutrition

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stunthink.presentation.component.card.NutritionCard
import com.example.stunthink.presentation.component.card.NutritionSummaryCard
import com.example.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.example.stunthink.presentation.ui.theme.StunThinkTheme
import com.example.stunthink.presentation.ui.theme.Typography

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
                    childNutritionViewModel.getNutritions(token = token, id = id)
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
                modifier = Modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    NutritionSummaryCard()
                }
                item {
                    Text(
                        text = "Makanan Hari Ini",
                        style = Typography.titleLarge
                    )
                }
                items(items = state.nutritions, itemContent = { child ->
                    NutritionCard(
                        image = null,
                        name = child.namaMakanan,
                        date = child.timastamp
                    ) { }
                })
            }

            FloatingActionButton(
                onClick = {
                    //OnClick Method
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                )
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