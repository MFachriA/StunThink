package com.example.stunthink.presentation.screen.monitoring.child.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stunthink.R
import com.example.stunthink.presentation.component.appbar.BackButtonAppBar
import com.example.stunthink.presentation.component.card.ChildCard
import com.example.stunthink.presentation.navigation.ScreenRoute
import com.example.stunthink.presentation.ui.theme.StunThinkTheme


@Composable
fun ChildListScreen(
    navController: NavController,
    viewModel: ChildListViewModel = hiltViewModel()
) {
    StunThinkTheme {
        val state = viewModel.state.value

        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = stringResource(id = R.string.child_list_title),
                    navigationOnClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    LazyColumn(
                        modifier = Modifier.fillMaxHeight(),
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
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