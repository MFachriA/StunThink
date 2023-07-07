package com.projectAnya.stunthink.presentation.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultBottomSheet(
    modifier: Modifier = Modifier,
    title: String = "",
    isOpened: Boolean,
    onDismissRequest: () -> Unit,
    showCloseButton: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    if (isOpened) {
        ModalBottomSheet(
            onDismissRequest = { onDismissRequest() },
            sheetState = sheetState,
            containerColor = Color.White,
            dragHandle = null
        ) {
            Column(
                modifier = modifier
                    .padding(start = 20.dp, top = 16.dp, bottom = 20.dp, end = 20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = title,
                        style = Typography.headlineSmall
                    )
                    if (showCloseButton) {
                        IconButton(
                            onClick = { scope.launch {
                                    sheetState.hide()
                                }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onDismissRequest()
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "ic_close"
                            )

                        }
                    }
                }
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                ){
                    content()
                }
            }
        }
    }
}