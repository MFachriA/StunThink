package com.projectAnya.stunthink.presentation.component.content.nutrition

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.projectAnya.stunthink.presentation.component.divider.DefaultDivider
import com.projectAnya.stunthink.presentation.component.selector.PortionSelector

@Composable
fun NutritionBottomBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    portion: Float,
    decreaseCallback: () -> Unit,
    increaseCallback: () -> Unit
) {
    Surface(modifier = modifier.zIndex(0f)) {
        Column {
            DefaultDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp)
                    .heightIn(min = 56.dp)
            ) {
                PortionSelector(
                    count = portion,
                    decreaseItemCount = { decreaseCallback() },
                    increaseItemCount = { increaseCallback() }
                )
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = { onClick() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Tambah Makanan",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}