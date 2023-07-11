package com.projectAnya.stunthink.presentation.component.content.nutrition

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.utils.NumberUtils

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
            text = "${NumberUtils.roundOffDecimal(amount)} $unit",
            style = Typography.titleMedium
        )
        Text(
            text = desc,
            style = Typography.bodyMedium
        )
    }
}