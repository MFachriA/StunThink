package com.projectAnya.stunthink.presentation.component.divider

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DefaultDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    thickness: Dp = 1.dp
) {
    Divider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
private fun DividerPreview() {
    Box(Modifier.size(height = 10.dp, width = 100.dp)) {
        DefaultDivider(Modifier.align(Alignment.Center))
    }
}