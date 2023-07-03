package com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.data.remote.dto.nutrition.FoodDto
import com.projectAnya.stunthink.presentation.component.card.NutritionCard
import com.projectAnya.stunthink.presentation.component.card.NutritionSummaryCard
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@Composable
fun ChildNutritionScreen(
    navController: NavController,
    mainViewModel: ChildMonitoringMainViewModel = hiltViewModel(),
    childNutritionViewModel: ChildNutritionViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val userTokenState: State<String?> = mainViewModel.userTokenState.collectAsState()
    val userToken: String? by userTokenState

    val childIdState: State<String?> = mainViewModel.childIdState.collectAsState()
    val childId: String? by childIdState

    LaunchedEffect(key1 = context) {
        userToken?.let { token ->
            childId?.let { id ->
                childNutritionViewModel.getNutritions(token, id)
            }
        }
    }

    val state = childNutritionViewModel.state.value

    StunThinkTheme {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .draggable(
                    state = mainViewModel.dragState.value!!,
                    orientation = Orientation.Horizontal,
                    onDragStarted = { },
                    onDragStopped = {
                        mainViewModel.updateTabIndexBasedOnSwipe()
                    }
                )
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Column {
                        Text(
                            modifier = Modifier.padding(bottom = 8.dp),
                            text = "Total Nutrisi Hari Ini",
                            style = Typography.titleLarge
                        )
                        NutritionSummaryCard()
                    }
                }
                item {
                    Text(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            bottom = 8.dp
                        ),
                        text = "Makanan Hari Ini",
                        style = Typography.titleLarge
                    )
                }
                if (state.nutritions.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 64.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.no_data_logo),
                                contentDescription = "image",
                                modifier = Modifier
                                    .height(120.dp)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "Belum ada data makanan",
                                style = Typography.bodyMedium
                            )
                        }
                    }
                } else {
                    items(items = state.nutritions, itemContent = { nutrition ->
                        NutritionCard(
                            image = nutrition.foodUrl,
                            name = nutrition.namaMakanan,
                            date = nutrition.timastamp
                        ) {
                            val food = FoodDto(
                                dataGizi = nutrition,
                                image = ""
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "food",
                                value = food
                            )
                            navController.navigate(
                                ScreenRoute.FoodDetail.route
                            )
                        }
                    })
                }
            }

            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        ScreenRoute.ChildFoodDetection.route
                    )
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

@Preview(showBackground = true)
@Composable
fun ChildNutritionScreenPreview() {
    ChildNutritionScreen(
        navController = rememberNavController()
    )
}
