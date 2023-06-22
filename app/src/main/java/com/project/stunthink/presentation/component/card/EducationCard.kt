package com.project.stunthink.presentation.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.project.stunthink.R
import com.project.stunthink.presentation.ui.theme.Typography

@Composable
fun EducationCard(
    modifier: Modifier = Modifier,
    imageLink: String,
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
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageLink)
                    .placeholder(R.drawable.stunthink_logo_background)
                    .error(R.drawable.stunthink_logo_background)
                    .crossfade(true)
                    .scale(Scale.FIT)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = Typography.titleMedium,
                )
                Text(
                    text = description,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}