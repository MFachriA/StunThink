package com.projectAnya.stunthink.presentation.screen.stunting

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.projectAnya.stunthink.domain.model.stunting.Stunting
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.component.dialog.ConfirmationDialog
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.stunting.ChildStuntingContent
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.utils.DateUtils

@Composable
fun StuntingDetailScreen(
    navController: NavController,
    stunting: Stunting?,
    viewModel: StuntingDetailViewModel = hiltViewModel()
) {
    val deleteDialogState: State<Boolean> = viewModel.deleteDialogState.collectAsState()
    val deleteDialog: Boolean by deleteDialogState

    val context = LocalContext.current
    val submitState = viewModel.submitState.value

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is StuntingDetailViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()
                    navController.popBackStack()
                }
                is StuntingDetailViewModel.ValidationEvent.Failed -> {
                    Toast.makeText(
                        context,
                        "Kesalahan terjadi, mohon ulangi kembali",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    StunThinkTheme {
        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = "Detail Stunting",
                    navigationOnClick = { navController.popBackStack() },
                    actions = {
                        IconButton(
                            onClick = {
                                viewModel.uploadDialogState(true)
                            },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "delete"
                                )
                            }
                        )
                    }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    StuntingDetailContent(stunting)

                    if (submitState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    ConfirmationDialog(
                        isOpen = deleteDialog,
                        onDismissRequest = {
                            viewModel.uploadDialogState(false)
                        },
                        onConfirmButton = {
                            stunting?.let {
                                viewModel.uploadDialogState(false)
                                viewModel.deleteStunting(stunting.id)
                            }
                        },
                        onDismissButton = {
                            viewModel.uploadDialogState(false)
                        },
                        title = "Hapus Pengukuran Stunting",
                        text = "Apakah kamu yakin ingin menghapus pengukuran stunting ini?"
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StuntingDetailContentPreview() {
    StuntingDetailContent(
        stunting = null
    )
}

@Composable
fun StuntingDetailContent(
    stunting: Stunting?
) {
    stunting?.let {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ChildStuntingContent(
                    title = "Level Stunting",
                    content = stunting.result.displayName
                )
                ChildStuntingContent(
                    title = "Tanggal Pengukuran",
                    content = DateUtils.formatDateTimeToIndonesianTimeDate(stunting.timestamp)
                )
                ChildStuntingContent(
                    title = "Tinggi",
                    content = "${stunting.tinggiBadan} cm"
                )
                ChildStuntingContent(
                    title = "Umur",
                    content = stunting.umur ?: "-"
                )
            }
        }
    }
}