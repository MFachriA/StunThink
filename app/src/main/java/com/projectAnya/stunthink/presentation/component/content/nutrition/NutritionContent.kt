package com.projectAnya.stunthink.presentation.component.content.nutrition

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@Composable
fun NutritionContent(
    amount: Float,
    unit: String,
    desc: String
) {
    val formattedAmount = String.format("%.2f", amount)
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$formattedAmount $unit",
            style = Typography.titleMedium
        )
        Text(
            text = desc,
            style = Typography.bodyMedium
        )
    }
}