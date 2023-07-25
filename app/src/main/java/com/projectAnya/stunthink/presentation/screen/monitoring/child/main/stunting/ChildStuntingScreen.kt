package com.projectAnya.stunthink.presentation.screen.monitoring.child.main.stunting

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DesignServices
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.presentation.component.bottomsheet.DefaultBottomSheet
import com.projectAnya.stunthink.presentation.component.card.StuntingCard
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.utils.DateUtils

@Composable
fun ChildStuntingScreen(
    navController: NavController,
    mainViewModel: ChildMonitoringMainViewModel = hiltViewModel(),
    childStuntingViewModel: ChildStuntingViewModel = hiltViewModel()
) {
    StunThinkTheme {
        val context = LocalContext.current

        val userTokenState: State<String?> = mainViewModel.userTokenState.collectAsState()
        val userToken: String? by userTokenState

        val childIdState: State<String?> = mainViewModel.childIdState.collectAsState()
        val childId: String? by childIdState


        LaunchedEffect(key1 = context) {
            userToken?.let { token ->
                childId?.let { id ->
                    childStuntingViewModel.getStuntings(token, id)
                }
            }
        }

        val state = childStuntingViewModel.state.value
        val stunting = state.stuntings.firstOrNull()

        var sheetState by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .draggable(
                    state = mainViewModel.dragState.value!!,
                    orientation = Orientation.Horizontal,
                    onDragStarted = { },
                    onDragStopped = {
                        mainViewModel.updateTabIndexBasedOnSwipe()
                    })
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (stunting != null) {
                    item {
                        Column(
                            modifier = Modifier.padding(bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(bottom = 4.dp),
                                text = "Pengukuran Terakhir",
                                style = Typography.titleLarge
                            )
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
                item {
                    Text(
                        modifier = Modifier.padding(
                            bottom = 4.dp
                        ),
                        text = "Riwayat Pengukuran Stunting",
                        style = Typography.titleLarge
                    )
                }
                if (state.stuntings.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 80.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.no_data_logo),
                                contentDescription = "image",
                                modifier = Modifier
                                    .height(120.dp)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "Belum ada data stunting",
                                style = Typography.bodyMedium
                            )
                        }
                    }
                } else {
                    items(items = state.stuntings, itemContent = { stunting ->
                        StuntingCard(
                            stuntLevel = stunting.result,
                            height = stunting.tinggiBadan,
                            date = stunting.timestamp,
                        ) {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "stunting",
                                value = stunting
                            )
                            navController.navigate(
                                ScreenRoute.StuntingDetail.route
                            )
                        }
                    })
                }
            }

            FloatingActionButton(
                onClick = {
                    sheetState = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.DesignServices,
                    contentDescription = "Add"
                )
            }
        }

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center)
                )
            }
        }

        DefaultBottomSheet(
            title = "Ukur Stunting",
            isOpened = sheetState,
            onDismissRequest = { sheetState = false }
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Dibutuhkan ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("tinggi anak ")
                    }
                    append("untuk melakukan pengukuran stunting, pilih cara untuk mengukur tinggi anak:")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                Button(
                    onClick = {
                        navController.navigate(ScreenRoute.ChildStuntingCamera.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                ) {
                    Text(text = "Foto Tinggi Anak")
                }
                Button(
                    onClick = {
                        navController.navigate(ScreenRoute.ChildStuntingDetection.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary,)
                ) {
                    Text(text = "Ukur Manual")
                }
            }
        }
    }
}

@Composable
fun ChildStuntingContent(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(text = title, style = Typography.bodyMedium)
        Text(text = content, style = Typography.titleMedium)
    }
}

