package com.projectAnya.stunthink.presentation.screen.monitoring.child.main.detail

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
import com.projectAnya.stunthink.data.remote.dto.child.ChildDto
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.component.dialog.ConfirmationDeleteDialog
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.stunting.ChildStuntingContent
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.utils.DateUtils
import com.projectAnya.stunthink.utils.StringUtils

@Composable
fun ChildMonitoringDetailScreen(
    navController: NavController,
    mainViewModel: ChildMonitoringMainViewModel = hiltViewModel(),
    viewModel: ChildMonitoringDetailViewModel = hiltViewModel()
) {
    val childIdState: State<String?> = mainViewModel.childIdState.collectAsState()
    val childId: String? by childIdState

    val deleteDialogState: State<Boolean> = viewModel.deleteDialogState.collectAsState()
    val deleteDialog: Boolean by deleteDialogState

    val childDetailState = viewModel.childDetailState.value
    val childDetail = childDetailState.childDetail

    val deleteState = viewModel.deleteState.value

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        childId?.let { id ->
            viewModel.getChildDetail(id)
        }
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ChildMonitoringDetailViewModel.DeleteValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(route = ScreenRoute.ChildList.route) {
                        popUpTo(ScreenRoute.ChildList.route) { inclusive = true }
                    }
                }
                is ChildMonitoringDetailViewModel.DeleteValidationEvent.Failed -> {
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
                    title = "Detail Anak",
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
                    ChildDetailContent(childDetail)

                    if (deleteState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }

                    ConfirmationDeleteDialog(
                        isOpen = deleteDialog,
                        onDismissRequest = {
                            viewModel.uploadDialogState(false)
                        },
                        onConfirmButton = {
                            childDetail?.let {
                                viewModel.uploadDialogState(false)
                                viewModel.deleteChild(childDetail.id)
                            }
                        },
                        onDismissButton = {
                            viewModel.uploadDialogState(false)
                        },
                        title = "Hapus Data Anak",
                        text = "Apakah kamu yakin ingin menghapus data anak ini? Data yang sudah terhapus tidak bisa dikembalikan lagi"
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StuntingDetailContentPreview() {
    ChildDetailContent(
        childDetail = null
    )
}

@Composable
fun ChildDetailContent(
    childDetail: ChildDto?
) {
    childDetail?.let {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ChildStuntingContent(
                    title = "Nama Lengkap",
                    content = childDetail.namaLengkap
                )
                ChildStuntingContent(
                    title = "Jenis Kelamin",
                    content = StringUtils.convertGenderEnum(childDetail.jenisKelamin)
                )
                ChildStuntingContent(
                    title = "Tanggal Lahir",
                    content = DateUtils.formatDateToIndonesianDate(childDetail.tanggalLahir)
                )
                ChildStuntingContent(
                    title = "Tempat Lahir",
                    content = childDetail.tempatLahir
                )
            }
        }
    }
}