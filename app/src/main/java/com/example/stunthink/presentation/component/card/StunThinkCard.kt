package com.example.stunthink.presentation.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.example.stunthink.presentation.ui.theme.Typography

@Composable
fun StuntThinkEducationCard(
    modifier: Modifier = Modifier,
    image: Painter,
    imageDescription: String? = null,
    text: String,
    description: String
    ) {
    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
        }
        Image(painter = image, contentDescription = imageDescription)
        Text(text = text, style = Typography.bodyMedium)
        Text(text = description, style = Typography.labelMedium)
    }
}


@Composable

fun StuntThinkEducationCardPreview(
    modifier: Modifier = Modifier,
    image: Painter,
    imageDescription: String? = null,
    text: String,
    description: String
) {
    Card {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
        Image(painter = image, contentDescription = imageDescription)
        Text(text = text, style = Typography.bodyMedium)
        Text(text = description, style = Typography.labelMedium)
    }
}