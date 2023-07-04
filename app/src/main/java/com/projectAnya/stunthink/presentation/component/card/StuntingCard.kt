package com.projectAnya.stunthink.presentation.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectAnya.stunthink.domain.model.stunting.StuntLevel
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.presentation.ui.theme.red_pentacle
import com.projectAnya.stunthink.presentation.ui.theme.red_republic
import com.projectAnya.stunthink.utils.DateUtils

@Composable
fun StuntingCard(
    modifier: Modifier = Modifier,
    stuntLevel: StuntLevel,
    height: Float,
    date: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = { onClick() },
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        contentPadding = PaddingValues()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = stuntLevel.displayName,
                    color = when(stuntLevel) {
                        StuntLevel.TALL -> MaterialTheme.colorScheme.primary
                        StuntLevel.NORMAL -> MaterialTheme.colorScheme.primary
                        StuntLevel.SEVERELY_STUNTED -> red_republic
                        StuntLevel.STUNTED -> red_pentacle
                        StuntLevel.OTHER -> Color.Black
                    },
                    style = Typography.labelLarge
                )
                Text(
                    text = "$height cm",
                    style = Typography.bodyMedium
                )
            }
            Text(
                text = DateUtils.formatDateTimeToIndonesianDate(date),
                style = Typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StuntingCardPreview() {
    StunThinkTheme() {
        StuntingCard(
            stuntLevel = StuntLevel.NORMAL,
            height = 10f,
            date = "2023-06-29T15:32:14.808Z"
        ) { }
    }
}