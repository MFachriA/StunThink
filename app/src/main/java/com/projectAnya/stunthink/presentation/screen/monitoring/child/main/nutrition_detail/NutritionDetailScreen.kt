package com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionDetailDto
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.component.card.NutritionSummary
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme

@Composable
fun NutritionDetailScreen(
    navController: NavController,
    nutritionStart: NutritionDetailDto?,
    nutritionTarget: NutritionDetailDto?
) {
    Scaffold(
        topBar = {
            BackButtonAppBar(
                title = "Detail Nutrisi",
                navigationOnClick = { navController.popBackStack() }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)
            ) {
               NutritionDetailContent(
                   nutritionStart = nutritionStart,
                   nutritionTarget = nutritionTarget
               )
            }
        }
    )
}


@Composable
private fun NutritionDetailContent(
    nutritionStart: NutritionDetailDto?,
    nutritionTarget: NutritionDetailDto?
) {
    StunThinkTheme {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            NutritionSummary(
                title = "Kalori",
                start = nutritionStart?.Energi,
                target = nutritionTarget?.Energi,
                prefix = "kkal"
            )
            NutritionSummary(
                title = "Karbohidrat",
                start = nutritionStart?.Karbohidrat,
                target = nutritionTarget?.Karbohidrat,
                prefix = "g"
            )
            NutritionSummary(
                title = "Protein",
                start = nutritionStart?.Protein,
                target = nutritionTarget?.Protein,
                prefix = "g"
            )
            NutritionSummary(
                title = "Lemak",
                start = nutritionStart?.Lemak,
                target = nutritionTarget?.Lemak,
                prefix = "g"
            )
            NutritionSummary(
                title = "Serat",
                start = nutritionStart?.Serat,
                target = nutritionTarget?.Serat,
                prefix = "g"
            )
            NutritionSummary(
                title = "Air",
                start = nutritionStart?.Air,
                target = nutritionTarget?.Air,
                prefix = "ml"
            )
            NutritionSummary(
                title = "Kalsium",
                start = nutritionStart?.Ca,
                target = nutritionTarget?.Ca,
                prefix = "g"
            )
            NutritionSummary(
                title = "Zat Besi",
                start = nutritionStart?.Zn2,
                target = nutritionTarget?.Zn2,
                prefix = "g"
            )
            NutritionSummary(
                title = "Zinc",
                start = nutritionStart?.Serat,
                target = nutritionTarget?.Serat,
                prefix = "g"
            )
            NutritionSummary(
                title = "Kalium",
                start = nutritionStart?.Ka,
                target = nutritionTarget?.Ka,
                prefix = "g"
            )
            NutritionSummary(
                title = "Natrium",
                start = nutritionStart?.Na,
                target = nutritionTarget?.Na,
                prefix = "g"
            )
            NutritionSummary(
                title = "Tembaga",
                start = nutritionStart?.Cu,
                target = nutritionTarget?.Cu,
                prefix = "g"
            )
            NutritionSummary(
                title = "Vitamin A",
                start = nutritionStart?.VitA,
                target = nutritionTarget?.VitA,
                prefix = "mg"
            )
            NutritionSummary(
                title = "Vitamin B1",
                start = nutritionStart?.VitB1,
                target = nutritionTarget?.VitB1,
                prefix = "mg"
            )
            NutritionSummary(
                title = "Vitamin B1",
                start = nutritionStart?.VitB1,
                target = nutritionTarget?.VitB1,
                prefix = "mg"
            )
            NutritionSummary(
                title = "Vitamin B2",
                start = nutritionStart?.VitB2,
                target = nutritionTarget?.VitB2,
                prefix = "mg"
            )
            NutritionSummary(
                title = "Vitamin B3",
                start = nutritionStart?.VitB3,
                target = nutritionTarget?.VitB3,
                prefix = "mg"
            )
            NutritionSummary(
                title = "Vitamin C",
                start = nutritionStart?.VitC,
                target = nutritionTarget?.VitC,
                prefix = "mg"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NutritionDetailContentPreview() {
    StunThinkTheme {
        NutritionDetailContent(
            nutritionStart =
            NutritionDetailDto(
                Air = 100f,
                Ca = 100f,
                Cu = 100f,
                Energi = 100f,
                F = 100f,
                Fe2 = 100f,
                Ka = 100f,
                Karbohidrat = 100f,
                Lemak = 100f,
                Na = 100f,
                Protein = 100f,
                Serat = 100f,
                VitA = 100f,
                VitB1 = 100f,
                VitB2 = 100f,
                VitB3 = 100f,
                VitC = 100f,
                Zn2 = 100f,
            ),
            nutritionTarget =
            NutritionDetailDto(
                Air = 200f,
                Ca = 100f,
                Cu = 100f,
                Energi = 100f,
                F = 200f,
                Fe2 = 200f,
                Ka = 200f,
                Karbohidrat = 200f,
                Lemak = 200f,
                Na = 200f,
                Protein = 200f,
                Serat = 200f,
                VitA = 100f,
                VitB1 = 100f,
                VitB2 = 100f,
                VitB3 = 100f,
                VitC = 100f,
                Zn2 = 100f,
            ),
        )
    }
}
