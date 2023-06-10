package com.example.stunthink.presentation.component.card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PersonAddAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stunthink.presentation.ui.theme.Typography

@Composable
fun MonitoringCard(
    modifier: Modifier,
    @DrawableRes image: Int,
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = { onClick() },
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                style = Typography.headlineSmall
            )
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier.height(100.dp)
            )
            Text(
                text = description,
                style = Typography.bodyMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Rounded.PersonAddAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = buttonText)
            }
        }
    }
}