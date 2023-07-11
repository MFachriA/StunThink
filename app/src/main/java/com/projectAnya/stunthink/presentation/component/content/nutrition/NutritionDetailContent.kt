package com.projectAnya.stunthink.presentation.component.content.nutrition

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.utils.NumberUtils

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
            text = "${NumberUtils.roundOffDecimal(amount)} $unit",
            style = Typography.titleMedium,
        )
    }
}