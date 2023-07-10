package com.projectAnya.stunthink.presentation.component.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@Composable
fun MonitoringCard(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = { onClick() },
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    style = Typography.headlineSmall,
                    color = Color.Black
                )
                Text(
                    text = description,
                    style = Typography.bodyLarge,
                    color = Color.Black
                )
            }
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .padding(8.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MonitoringCardPreview() {
    StunThinkTheme {
        MonitoringCard(
            modifier = Modifier,
            title = "Monitoring Anak",
            description = "Catatan makanan dan nutrisi \nharian ibu",
            image = R.drawable.mother_baby_illustration
        ) { }
    }
}