package com.project.stunthink.presentation.screen.main.education.detail

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.project.stunthink.R
import com.project.stunthink.presentation.component.appbar.BackButtonAppBar
import com.project.stunthink.presentation.ui.theme.StunThinkTheme
import com.project.stunthink.presentation.ui.theme.Typography

@Composable
fun EducationDetailScreen(
    navController: NavController,
    education: com.project.stunthink.data.remote.dto.education.EducationDto?
) {
    StunThinkTheme {
        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = "Edukasi",
                    navigationOnClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    EducationDetailContent(education)
                }
            }
        )
    }
}

@Composable
private fun EducationDetailContent(
    education: com.project.stunthink.data.remote.dto.education.EducationDto?
) {
    education?.let {
        val htmlDescription = remember(education.content) {
            HtmlCompat.fromHtml(education.content, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = education.title,
                        style = Typography.titleLarge
                    )
                    Text(
                        text = education.author,
                        style = Typography.labelLarge
                    )
                    Text(
                        text = education.publishedAt,
                        style = Typography.bodyMedium
                    )
                }
            }
            item {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(education.urlToImage)
                        .placeholder(R.drawable.stunthink_logo_background)
                        .error(R.drawable.stunthink_logo_background)
                        .crossfade(true)
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillHeight
                )
            }
            item {
                AndroidView(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    factory = { context ->
                        TextView(context).apply {
                            movementMethod = LinkMovementMethod.getInstance()
                        }
                    },
                    update = {
                        it.text = htmlDescription
                        it.textSize = Typography.bodyLarge.fontSize.value
                        it.setTextColor(it.context.getColor(R.color.black))
                    }
                )
            }
        }
    }
}