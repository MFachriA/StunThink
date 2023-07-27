package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy.detail

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.projectAnya.stunthink.domain.model.pregnancy.Pregnancy
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.component.dialog.ConfirmationDialog
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy.MotherPregnancyContent
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.utils.DateUtils
import com.projectAnya.stunthink.utils.StringUtils

@Composable
fun PregnancyDetailScreen(
    navController: NavController,
    pregnancy: Pregnancy?,
    viewModel: PregnancyDetailViewModel = hiltViewModel()
) {
    val deleteDialogState: State<Boolean> = viewModel.deleteDialogState.collectAsState()
    val deleteDialog: Boolean by deleteDialogState

    val updateDialogState: State<Boolean> = viewModel.updateDialogState.collectAsState()
    val updateDialog: Boolean by updateDialogState

    val context = LocalContext.current
    val submitState = viewModel.submitState.value

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is PregnancyDetailViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()
                    navController.popBackStack()
                }
                is PregnancyDetailViewModel.ValidationEvent.Failed -> {
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
                    title = "Detail Kehamilan",
                    navigationOnClick = { navController.popBackStack() },
                    actions = {
                        IconButton(
                            onClick = {
                                viewModel.changeDeleteDialogState(true)
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
                    StuntingDetailContent(pregnancy = pregnancy) {
                        viewModel.changeUpdateDialogState(true)
                    }

                    if (submitState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    ConfirmationDialog(
                        isOpen = deleteDialog,
                        onDismissRequest = {
                            viewModel.changeDeleteDialogState(false)
                        },
                        onConfirmButton = {
                            pregnancy?.let {
                                viewModel.changeDeleteDialogState(false)
                                viewModel.deleteStunting(pregnancy.id)
                            }
                        },
                        onDismissButton = {
                            viewModel.changeDeleteDialogState(false)
                        },
                        title = "Hapus Data Kehamilan",
                        text = "Apakah kamu yakin ingin menghapus data kehamilan ini?"
                    )

                    ConfirmationDialog(
                        isOpen = updateDialog,
                        onDismissRequest = {
                            viewModel.changeUpdateDialogState(false)
                        },
                        onConfirmButton = {
                            pregnancy?.let {
                                viewModel.changeUpdateDialogState(false)
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "pregnancy",
                                    value = pregnancy
                                )
                                navController.navigate(ScreenRoute.EditPregnancy.route)
                            }
                        },
                        onDismissButton = {
                            viewModel.changeUpdateDialogState(false)
                        },
                        icon =  Icons.Default.Edit,
                        contentColor = MaterialTheme.colorScheme.primary,
                        title = "Ubah Data Kehamilan",
                        text = "Ubah data ketika status kehamilan anda berubah, apakah anak anda sudah lahir?"
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
        pregnancy = null,
        updateCallback = {}
    )
}

@Composable
fun StuntingDetailContent(
    pregnancy: Pregnancy?,
    updateCallback: () -> Unit
) {
    pregnancy?.let {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MotherPregnancyContent(
                    title = "Status Kehamilan",
                    content = StringUtils.convertPregnancyType(pregnancy.pregnancyType)
                )
                MotherPregnancyContent(
                    title = "Tanggal Hamil",
                    content = DateUtils.formatDateTimeToIndonesianDate(pregnancy.pregnantDate)
                )
                MotherPregnancyContent(
                    title = "Tanggal Lahir",
                    content = pregnancy.birthDate?.let {
                        DateUtils.formatDateTimeToIndonesianDate(it)
                    } ?: run {
                        "-"
                    }
                )
            }
            Button(
                onClick = { updateCallback() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary,
                ),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Ubah Data")
            }
        }
    }
}