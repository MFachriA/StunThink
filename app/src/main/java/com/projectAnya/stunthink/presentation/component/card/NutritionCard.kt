package com.projectAnya.stunthink.presentation.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.utils.DateUtils

@Composable
fun NutritionCard(
    modifier: Modifier = Modifier,
    image: Any? = null,
    name: String,
    date: String,
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
            image?.let { image ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(text = name, style = Typography.labelLarge)
                Text(text = DateUtils.formatDateTimeToIndonesianTimeDate(date), style = Typography.bodyMedium)
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "back",
                modifier = Modifier.padding(end = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NutritioNCardPreview() {
    StunThinkTheme() {
        NutritionCard(
            name = "Muhammad Fachri Akbar",
            date = "2023-06-29T15:32:14.808Z"
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
fun NutritioNCardWithImagePreview() {
    StunThinkTheme() {
        NutritionCard(
            image = "https://i0.wp.com/beritajatim.com/wp-content/uploads/2023/01/IMG_20230119_173726.jpg?w=800&ssl=1",
            name = "Muhammad Fachri Akbar",
            date = "2023-06-29T15:32:14.808Z"
        ) { }
    }
}