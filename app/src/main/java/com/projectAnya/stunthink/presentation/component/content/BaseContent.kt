package com.projectAnya.stunthink.presentation.component.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@Composable
fun BaseContent(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable (BoxScope.() -> Unit)
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = title,
            style = Typography.titleLarge
        )
        Box {
            content()
        }
    }
}