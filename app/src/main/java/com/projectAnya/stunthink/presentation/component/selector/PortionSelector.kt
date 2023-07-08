package com.projectAnya.stunthink.presentation.component.selector

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PortionSelector(
    count: Float,
    decreaseItemCount: () -> Unit,
    increaseItemCount: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = "Porsi",
            modifier = Modifier
                .padding(end = 18.dp)
                .align(Alignment.CenterVertically)
        )
        IconButton(
            onClick = decreaseItemCount,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                modifier = Modifier.border(
                    1.dp,
                    Color.Black,
                    shape = CircleShape
                ),
                imageVector = Icons.Default.Remove,
                contentDescription = "remove"
            )
        }
        Crossfade(
            targetState = count,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "$it",
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(min = 24.dp)
            )
        }
        IconButton(
            onClick = increaseItemCount,
            modifier = Modifier
                .align(Alignment.CenterVertically)

        ) {
            Icon(
                modifier = Modifier.border(
                    1.dp,
                    Color.Black,
                    shape = CircleShape
                ),
                imageVector = Icons.Default.Add,
                contentDescription = "add"
            )
        }
    }
}