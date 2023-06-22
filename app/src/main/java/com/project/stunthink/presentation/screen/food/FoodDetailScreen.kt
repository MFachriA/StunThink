package com.project.stunthink.presentation.screen.food

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.project.stunthink.R
import com.project.stunthink.data.remote.dto.nutrition.FoodDto
import com.project.stunthink.presentation.component.appbar.BackButtonAppBar
import com.project.stunthink.presentation.ui.theme.StunThinkTheme
import com.project.stunthink.presentation.ui.theme.Typography

@Composable
fun FoodDetailScreen(
    navController: NavController,
    food: FoodDto?
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
                    FoodDetailContent(food)
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
    food: FoodDto?
) {
    var showDetail by rememberSaveable { mutableStateOf(false) }
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

    food?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
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
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
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
            style = Typography.bodySmall
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
            style = Typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "$amount $unit",
            style = Typography.titleMedium,
        )
    }
}