package com.projectAnya.stunthink.presentation.screen.food

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.data.remote.dto.nutrition.FoodDto
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.component.divider.DefaultDivider
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@Composable
fun FoodDetailScreen(
    navController: NavController,
    food: FoodDto?,
    viewModel: ChildMonitoringMainViewModel = hiltViewModel(),
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel()
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
    FoodDetailScreen(
        navController = rememberNavController(),
        null
    )
}

@Composable
fun FoodDetailContent(
    navController: NavController,
    food: FoodDto?,
    viewModel: ChildMonitoringMainViewModel = hiltViewModel(),
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel()
) {
    val userTokenState: State<String?> = viewModel.userTokenState.collectAsState()
    val userToken: String? by userTokenState

    var showDetail by rememberSaveable { mutableStateOf(false) }

    val childIdState: State<String?> = viewModel.childIdState.collectAsState()
    val childId: String? by childIdState

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
                is FoodDetailViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    navController.navigate(route = ScreenRoute.ChildMonitoringMain.route) {
                        popUpTo(ScreenRoute.ChildMonitoringMain.route) { inclusive = true }
                    }
                }
                is FoodDetailViewModel.ValidationEvent.Failed -> {
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
                            amount = food.dataGizi.Energi,
                            unit = "kkal",
                            desc = "Kalori"
                        )
                        NutritionContent(
                            amount = food.dataGizi.Karbohidrat,
                            unit = "g",
                            desc = "Karbohidrat"
                        )
                        NutritionContent(
                            amount = food.dataGizi.Protein,
                            unit = "g",
                            desc = "Protein"
                        )
                        NutritionContent(
                            amount = food.dataGizi.Lemak,
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
                                amount = food.dataGizi.Energi,
                                unit = "kkal",
                                desc = "Kalori"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Karbohidrat,
                                unit = "g",
                                desc = "Karbohidrat"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Protein,
                                unit = "g",
                                desc = "Protein"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Lemak,
                                unit = "g",
                                desc = "Lemak"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Serat,
                                unit = "g",
                                desc = "Serat"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Air,
                                unit = "ml",
                                desc = "Air"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Ca,
                                unit = "g",
                                desc = "Kalsium"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Zn2,
                                unit = "g",
                                desc = "Zat Besi"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Serat,
                                unit = "g",
                                desc = "Zinc"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Ka,
                                unit = "g",
                                desc = "Kalium"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Na,
                                unit = "g",
                                desc = "Natrium"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.Cu,
                                unit = "g",
                                desc = "Tembaga"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.VitA,
                                unit = "mg",
                                desc = "Vitamin A"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.VitB1,
                                unit = "mg",
                                desc = "Vitamin B1"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.VitB2,
                                unit = "mg",
                                desc = "Vitamin B2"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.VitB3,
                                unit = "mg",
                                desc = "Vitamin B3"
                            )
                            NutritionDetailContent(
                                amount = food.dataGizi.VitC,
                                unit = "mg",
                                desc = "Vitamin C"
                            )
                        }
                    }
                }
            }
        }
        if (food?.dataGizi?.id?.isNotEmpty() == true) {
            NutritionBottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = {
                    foodDetailViewModel.onSubmit(
                        token = userToken!!,
                        id = childId!!,
                        foodPercentage = foodDetailViewModel.foodPortionState.value,
                        foodId = food.dataGizi.id,
                        foodImageUrl = food.image
                    )
                }
            )
        }
        if (submitState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun NutritionContent(
    amount: Float,
    unit: String,
    desc: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$amount $unit",
            style = Typography.titleMedium
        )
        Text(
            text = desc,
            style = Typography.bodyMedium
        )
    }
}

@Composable
fun NutritionDetailContent(
    amount: Float,
    unit: String,
    desc: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = desc,
            style = Typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "$amount $unit",
            style = Typography.titleMedium,
        )
    }
}

@Composable
fun NutritionBottomBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    foodDetailViewModel: FoodDetailViewModel = hiltViewModel()
) {
    val foodPortionState = foodDetailViewModel.foodPortionState.collectAsState()
    val foodPortion = foodPortionState.value

    Surface(modifier = modifier.zIndex(0f)) {
        Column {
            DefaultDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp)
                    .heightIn(min = 56.dp)
            ) {
                PortionSelector(
                    count = foodPortion,
                    decreaseItemCount = {
                        if (foodPortion > 0)
                            foodDetailViewModel.updateFoodPortion(foodPortion - 0.5f)
                    },
                    increaseItemCount = {
                        foodDetailViewModel.updateFoodPortion(foodPortion + 0.5f)
                    }
                )
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = { onClick() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Tambah Makanan",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }

    }
}

@Composable
fun PortionSelector(
    count: Float,
    decreaseItemCount: () -> Unit,
    increaseItemCount: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = "Porsi",
            modifier = Modifier
                .padding(end = 18.dp)
                .align(Alignment.CenterVertically)
        )
        IconButton(
            onClick = decreaseItemCount,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                modifier = Modifier.border(
                    1.dp,
                    Color.Black,
                    shape = CircleShape
                ),
                imageVector = Icons.Default.Remove,
                contentDescription = "remove"
            )
        }
        Crossfade(
            targetState = count,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "$it",
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(min = 24.dp)
            )
        }
        IconButton(
            onClick = increaseItemCount,
            modifier = Modifier
                .align(Alignment.CenterVertically)

        ) {
            Icon(
                modifier = Modifier.border(
                    1.dp,
                    Color.Black,
                    shape = CircleShape
                ),
                imageVector = Icons.Default.Add,
                contentDescription = "add"
            )
        }
    }
}