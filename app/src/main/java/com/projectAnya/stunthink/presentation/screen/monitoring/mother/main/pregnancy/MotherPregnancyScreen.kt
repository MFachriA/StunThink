package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy

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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.projectAnya.stunthink.presentation.component.card.PregnancyCard
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.MotherMonitoringMainViewModel
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.utils.DateUtils
import com.projectAnya.stunthink.utils.StringUtils

@Composable
fun MotherPregnancyScreen(
    navController: NavController,
    mainViewModel: MotherMonitoringMainViewModel = hiltViewModel()
) {
    StunThinkTheme {
        val context = LocalContext.current

        val userTokenState: State<String?> = mainViewModel.userTokenState.collectAsState()
        val userToken: String? by userTokenState

        val state = mainViewModel.pregnancyState.value
        val pregnancyList = state.pregnancyList
        val firstPregnancy = pregnancyList.firstOrNull()

        LaunchedEffect(key1 = context) {
            userToken?.let { token ->
                mainViewModel.getPregnancyList(token)
            }
        }

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
                if (firstPregnancy != null) {
                    item {
                        Column(
                            modifier = Modifier.padding(bottom = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(bottom = 4.dp),
                                text = "Kehamilan Terbaru",
                                style = Typography.titleLarge
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                MotherPregnancyContent(
                                    title = "Status Kehamilan",
                                    content = StringUtils.convertPregnancyType(firstPregnancy.pregnancyType)
                                )
                                MotherPregnancyContent(
                                    title = "Tanggal Hamil",
                                    content = DateUtils.formatDateTimeToIndonesianDate(firstPregnancy.pregnantDate)
                                )
                                MotherPregnancyContent(
                                    title = "Tanggal Lahir",
                                    content = firstPregnancy.birthDate?.let {
                                        DateUtils.formatDateTimeToIndonesianDate(it)
                                    } ?: run {
                                        "-"
                                    }
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
                        text = "Riwayat Kehamilan",
                        style = Typography.titleLarge
                    )
                }
                if (pregnancyList.isEmpty()) {
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
                                text = "Belum ada data kehamilan",
                                style = Typography.bodyMedium
                            )
                        }
                    }
                } else {
                    items(items = pregnancyList, itemContent = { pregnancy ->
                        PregnancyCard(
                            type = pregnancy.pregnancyType,
                            date = pregnancy.pregnantDate
                        ) {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "pregnancy",
                                value = pregnancy
                            )
                            navController.navigate(ScreenRoute.PregnancyDetail.route)
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
                    imageVector = Icons.Rounded.Add,
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
            title = "Tambah Data Kehamilan",
            isOpened = sheetState,
            onDismissRequest = { sheetState = false }
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Jika sedang ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("hamil")
                    }
                    append(", ayo tambah data kehamilan anda agar monitoring nutrisi dapat disesuaikan!")
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    navController.navigate(ScreenRoute.AddPregnancy.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
            ) {
                Text(text = "Tambah")
            }
        }
    }
}

@Composable
fun MotherPregnancyContent(
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

