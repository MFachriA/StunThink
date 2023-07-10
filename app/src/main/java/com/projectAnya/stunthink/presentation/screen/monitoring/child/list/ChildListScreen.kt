package com.projectAnya.stunthink.presentation.screen.monitoring.child.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.component.card.ChildCard
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.navigation.start.StartViewModel
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.ChildMonitoringMainViewModel
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.utils.rememberLifecycleEvent


@Composable
fun ChildListScreen(
    navController: NavController,
    startViewModel: StartViewModel = hiltViewModel(),
    childListViewModel: ChildListViewModel = hiltViewModel(),
    childMonitoringViewModel: ChildMonitoringMainViewModel = hiltViewModel()
) {
    val state = childListViewModel.state.value
    val lifecycleEvent = rememberLifecycleEvent()

    val userTokenState: State<String?> = startViewModel.userTokenState.collectAsState()
    val userToken: String? by userTokenState

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            userToken?.let { token ->
                childListViewModel.getChilds(token)
            }
        }
    }


    StunThinkTheme {

        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = stringResource(id = R.string.child_list_title),
                    navigationOnClick = { navController.popBackStack() },
                    actions = {
                        IconButton(
                            onClick = { navController.navigate(ScreenRoute.ChildRegister.route) },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "back"
                                )
                            }
                        )
                    }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    if (state.childList.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .align(Alignment.Center),
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
                                text = "Belum ada data anak",
                                style = Typography.bodyMedium
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxHeight(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(items = state.childList, itemContent = { child ->
                                ChildCard(
                                    name = child.namaLengkap,
                                    gender = child.jenisKelamin,
                                    dateOfBirth = child.tanggalLahir
                                ) {
                                    childMonitoringViewModel.setChildId(child.id)
                                    childMonitoringViewModel.setChildName(child.namaLengkap)
                                    navController.navigate(
                                        route = ScreenRoute.ChildMonitoringMain.route
                                    )
                                }
                            })
                        }

                    }
                    if (state.isLoading) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier
                                .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChildListScreenPreview() {
    ChildListScreen(
        navController = rememberNavController()
    )
}