package com.projectAnya.stunthink.presentation.screen.food.monitoring

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.data.remote.dto.nutrition.FoodDto
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.component.content.nutrition.NutritionBottomBar
import com.projectAnya.stunthink.presentation.component.content.nutrition.NutritionContent
import com.projectAnya.stunthink.presentation.component.content.nutrition.NutritionDetailContent
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@Composable
fun MonitoringFoodDetailScreen(
    navController: NavController,
    food: FoodDto?,
    viewModel: ChildMonitoringMainViewModel = hiltViewModel(),
    foodDetailViewModel: MonitoringFoodDetailViewModel = hiltViewModel()
) {
    StunThinkTheme {
        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = "Detail Makanan",
                    navigationOnClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    FoodDetailContent(
                        navController, 
                        food,
                        viewModel,
                        foodDetailViewModel
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailScreenPreview() {
    MonitoringFoodDetailScreen(
        navController = rememberNavController(),
        null
    )
}

@Composable
fun FoodDetailContent(
    navController: NavController,
    food: FoodDto?,
    viewModel: ChildMonitoringMainViewModel = hiltViewModel(),
    foodDetailViewModel: MonitoringFoodDetailViewModel = hiltViewModel()
) {
    val userTokenState: State<String?> = viewModel.userTokenState.collectAsState()
    val userToken: String? by userTokenState

    var showDetail by rememberSaveable { mutableStateOf(true) }

    val childIdState: State<String?> = viewModel.childIdState.collectAsState()
    val childId: String? by childIdState

    val foodPortionState = foodDetailViewModel.foodPortionState.collectAsState()
    val foodPortion = foodPortionState.value

    val detailText = if (showDetail) {
        "Sembunyikan Detail"
    } else {
        "Tampilkan Detail"
    }

    val detailIcon = if (showDetail) {
        Icons.Filled.ExpandLess
    } else {
        Icons.Filled.ExpandMore
    }

    val context = LocalContext.current

    val submitState = foodDetailViewModel.submitState.value

    LaunchedEffect(key1 = context) {
        foodDetailViewModel.validationEvents.collect { event ->
            when (event) {
                is MonitoringFoodDetailViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    if (childId == null) {
                        navController.navigate(route = ScreenRoute.MotherMonitoringMain.route) {
                            popUpTo(ScreenRoute.MotherMonitoringMain.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(route = ScreenRoute.ChildMonitoringMain.route) {
                            popUpTo(ScreenRoute.ChildMonitoringMain.route) { inclusive = true }
                        }
                    }
                }
                is MonitoringFoodDetailViewModel.ValidationEvent.Failed -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxHeight()) {
            food?.let {
                val imageLink =
                    if (food.image?.isEmpty() == false) food.image else food.dataGizi.foodUrl
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageLink)
                        .placeholder(R.drawable.stunthink_logo_background)
                        .error(R.drawable.stunthink_logo_background)
                        .crossfade(true)
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .height(300.dp)
                        .padding(bottom = 16.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = food.dataGizi.namaMakanan,
                        modifier = Modifier.fillMaxWidth(),
                        style = Typography.titleLarge,
                        textAlign = TextAlign.Start
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement
                            .SpaceAround,
                    ) {
                        NutritionContent(
                            amount = food.dataGizi.Energi * foodPortion,
                            unit = "kkal",
                            desc = "Kalori"
                        )
                        NutritionContent(
                            amount = food.dataGizi.Karbohidrat * foodPortion,
                            unit = "g",
                            desc = "Karbohidrat"
                        )
                        NutritionContent(
                            amount = food.dataGizi.Protein * foodPortion,
                            unit = "g",
                            desc = "Protein"
                        )
                        NutritionContent(
                            amount = food.dataGizi.Lemak * foodPortion,
                            unit = "g",
                            desc = "Lemak"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                showDetail = !showDetail
                            }
                            .animateContentSize(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = detailText,
                            style = Typography.bodyMedium
                        )
                        Icon(
                            imageVector = detailIcon,
                            contentDescription = null
                        )
                    }
                    AnimatedVisibility(visible = showDetail) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Detail Nutrisi",
                                modifier = Modifier.fillMaxWidth(),
                                style = Typography.titleLarge,
                                textAlign = TextAlign.Start
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Energi * foodPortion,
                                unit = "kkal",
                                desc = "Kalori"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Karbohidrat * foodPortion,
                                unit = "g",
                                desc = "Karbohidrat"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Protein * foodPortion,
                                unit = "g",
                                desc = "Protein"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Lemak * foodPortion,
                                unit = "g",
                                desc = "Lemak"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Serat * foodPortion,
                                unit = "g",
                                desc = "Serat"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Air * foodPortion,
                                unit = "ml",
                                desc = "Air"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Ca * foodPortion,
                                unit = "g",
                                desc = "Kalsium"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Zn2 * foodPortion,
                                unit = "g",
                                desc = "Zat Besi"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Serat * foodPortion,
                                unit = "g",
                                desc = "Zinc"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Ka * foodPortion,
                                unit = "g",
                                desc = "Kalium"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Na * foodPortion,
                                unit = "g",
                                desc = "Natrium"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Cu * foodPortion,
                                unit = "g",
                                desc = "Tembaga"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.VitA * foodPortion,
                                unit = "mg",
                                desc = "Vitamin A"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.VitB1 * foodPortion,
                                unit = "mg",
                                desc = "Vitamin B1"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.VitB2 * foodPortion,
                                unit = "mg",
                                desc = "Vitamin B2"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.VitB3 * foodPortion,
                                unit = "mg",
                                desc = "Vitamin B3"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.VitC * foodPortion,
                                unit = "mg",
                                desc = "Vitamin C"
                            )
                        }
                    }
                }
            }
        }
        userToken?.let { token ->
            food?.let {
                NutritionBottomBar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onClick = {
                        if (childId == null) {
                            foodDetailViewModel.submitMotherFood(
                                token = token,
                                foodPercentage = foodDetailViewModel.foodPortionState.value,
                                foodId = it.dataGizi.id,
                                foodImageUrl = it.image
                            )
                        } else {
                            foodDetailViewModel.submitChildFood(
                                token = token,
                                id = childId!!,
                                foodPercentage = foodDetailViewModel.foodPortionState.value,
                                foodId = it.dataGizi.id,
                                foodImageUrl = it.image
                            )
                        }
                    },
                    portion = foodPortion,
                    decreaseCallback = {
                        if (foodPortion > 0)
                            foodDetailViewModel.updateFoodPortion(foodPortion - 0.25f)
                    },
                    increaseCallback = {
                        foodDetailViewModel.updateFoodPortion(foodPortion + 0.25f)
                    }
                )
            }
        }
        if (submitState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}