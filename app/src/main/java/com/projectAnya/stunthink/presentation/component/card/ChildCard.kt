package com.projectAnya.stunthink.presentation.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@Composable
fun ChildCard(
    modifier: Modifier = Modifier,
    name: String,
    gender: String,
    dateOfBirth: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = { onClick() },
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(text = name, style = Typography.labelLarge)
                Text(text = gender, style = Typography.bodyMedium)
            }
            Text(text = dateOfBirth, style = Typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChildCardPreview() {
    StunThinkTheme() {
        ChildCard(
            name = "Muhammad Fachri Akbar",
            gender = "M",
            dateOfBirth = "2001-09-09"
        ) { }
    }
}