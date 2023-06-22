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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import kotlin.random.Random

@Composable
fun NutritionSummaryCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            NutritionSummary(title = "Kalori", value = 300f, target = 500f)
            NutritionSummary(title = "Energi", value = 400f, target = 400f)
            NutritionSummary(title = "Vitamin", value = 200f, target = 320f)
            NutritionSummary(title = "Mineral", value = 50f, target = 70f)
        }
    }
}

@Composable
fun NutritionSummary(
    modifier: Modifier = Modifier,
    title: String,
    value: Float,
    target: Float
){
    val progress: Float by animateFloatAsState(Random.nextFloat())

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    style = Typography.titleSmall
                )
                Text(
                    text = "$value/$target",
                    style = Typography.bodySmall
                )
            }
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                trackColor = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NutritionSummaryCardPreview() {
    StunThinkTheme() {
        NutritionSummaryCard()
    }
}