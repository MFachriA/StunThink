package com.example.stunthink.presentation.screen.monitoring.child.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stunthink.R
import com.example.stunthink.presentation.component.appbar.BackButtonAppBar
import com.example.stunthink.presentation.component.card.ChildCard
import com.example.stunthink.presentation.navigation.ScreenRoute
import com.example.stunthink.presentation.navigation.start.StartViewModel
import com.example.stunthink.presentation.ui.theme.StunThinkTheme
import com.example.stunthink.utils.rememberLifecycleEvent


@Composable
fun ChildListScreen(
    navController: NavController,
    startViewModel: StartViewModel = hiltViewModel(),
    childListViewModel: ChildListViewModel = hiltViewModel()
) {
    val state = childListViewModel.state.value
    val lifecycleEvent = rememberLifecycleEvent()
    val token by startViewModel.userToken.collectAsState()

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            token?.let { token ->
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
                                navController.navigate(
                                    route = ScreenRoute.ChildMonitoringMain.passIdAndName(
                                        id = child.id,
                                        name = child.namaLengkap
                                    )
                                )
                            }
                        })
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