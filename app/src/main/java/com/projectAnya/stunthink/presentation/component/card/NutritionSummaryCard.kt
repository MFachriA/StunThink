package com.projectAnya.stunthink.presentation.component.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionDetailDto
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.presentation.ui.theme.capitalino_cactus

@Composable
fun NutritionSummaryCard(
    modifier: Modifier = Modifier,
    startNutrition: NutritionDetailDto?,
    targetNutrition: NutritionDetailDto?
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            NutritionSummary(
                title = "Kalori",
                start = startNutrition?.Energi ?: 0f,
                target = targetNutrition?.Energi ?: 0f,
                prefix = "kkal"
            )
            NutritionSummary(
                title = "Karbohidrat",
                start = startNutrition?.Karbohidrat ?: 0f,
                target = targetNutrition?.Karbohidrat ?: 0f,
                prefix = "g"
            )
            NutritionSummary(
                title = "Protein",
                start = startNutrition?.Protein ?: 0f,
                target = targetNutrition?.Protein ?: 0f,
                prefix = "g"
            )
            NutritionSummary(
                title = "Lemak",
                start = startNutrition?.Lemak ?: 0f,
                target = targetNutrition?.Lemak ?: 0f,
                prefix = "g"
            )
        }
    }
}

@Composable
fun NutritionSummary(
    modifier: Modifier = Modifier,
    title: String,
    start: Float?,
    target: Float?,
    prefix: String
){
    val mStart = start ?: 0f
    val mTarget = target ?: 0f

    val progress: Float by animateFloatAsState(mStart/mTarget)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row {
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = Typography.titleSmall
            )
            Text(
                text = "$mStart/$mTarget $prefix",
                style = Typography.bodySmall
            )
        }
        LinearProgressIndicator(
            progress = if (!progress.isNaN()) progress else 0f,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = if (progress == 1f) capitalino_cactus else MaterialTheme.colorScheme.secondary,
            trackColor = Color.Gray
        )
    }
}