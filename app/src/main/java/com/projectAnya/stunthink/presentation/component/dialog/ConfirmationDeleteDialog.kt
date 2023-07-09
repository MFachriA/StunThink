package com.projectAnya.stunthink.presentation.component.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@Composable
fun ConfirmationDeleteDialog(
    isOpen: Boolean,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
    onDismissButton: () -> Unit,
    icon: ImageVector = Icons.Default.Cancel,
    title: String,
    text: String
) {
    StunThinkTheme {
        if (isOpen) {
            AlertDialog(
                modifier = modifier,
                onDismissRequest = onDismissRequest,
                confirmButton = {
                    Text(
                        modifier = Modifier.clickable {
                            onConfirmButton()
                        },
                        text = "Ya",
                        style = Typography.labelLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                },
                dismissButton = {
                    Text(
                        modifier = Modifier.clickable {
                            onDismissButton()
                        },
                        text = "Tidak",
                        style = Typography.labelLarge,
                        color = Color.DarkGray
                    )
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                title = {
                    Text(
                        text = title,
                        style = Typography.headlineSmall
                    )
                },
                text = {
                    Text(
                        text = text,
                        style = Typography.bodyMedium
                    )
                },
                containerColor = Color.White
            )
        }
    }
}