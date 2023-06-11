package com.example.stunthink.presentation.component.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackButtonAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigationOnClick: ()-> Unit,
    actions: @Composable (RowScope.() -> Unit)? = null
    ) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = { navigationOnClick() },
                content = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back"
                    )
                }
            )
        },
        actions = {
            actions?.let {
                actions()
            }
        }
    )
}

