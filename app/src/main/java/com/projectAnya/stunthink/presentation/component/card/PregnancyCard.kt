package com.projectAnya.stunthink.presentation.component.card

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectAnya.stunthink.domain.model.pregnancy.PregnancyType
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.utils.DateUtils
import com.projectAnya.stunthink.utils.StringUtils

@Composable
fun PregnancyCard(
    modifier: Modifier = Modifier,
    date: String,
    type: PregnancyType,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier.heightIn(min = 65.dp),
        onClick = { onClick() },
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = DateUtils.formatDateTimeToIndonesianDate(date),
                style = Typography.labelLarge,
                color = Color.Black
            )
            Text(
                text = StringUtils.convertPregnancyType(type),
                style = Typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PregnancyCardPreview() {
    StunThinkTheme() {
        PregnancyCard(
            date = "2023-01-01T00:00:00.000Z",
            type = PregnancyType.BORN,
        ) { }
    }
}