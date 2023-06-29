package com.projectAnya.stunthink.presentation.screen.monitoring.child.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition.ChildNutritionScreen
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.stunting.ChildStuntingScreen
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildMonitoringMainScreen(
    navController: NavController,
    id: String = "",
    name: String = "",
    viewModel: ChildMonitoringMainViewModel = hiltViewModel()
) {
    val tabIndex = viewModel.tabIndex.observeAsState()

    LaunchedEffect(key1 = id) {
        viewModel.setChildId(id = id)
    }

    StunThinkTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = stringResource(id = R.string.child_monitoring_title)
                            )
                            Text(
                                text = name,
                                style = Typography.labelMedium
                            )
                        }
                    },
                    modifier = Modifier,
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            content = {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "back"
                                )
                            }
                        )
                    },
                    actions = { }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                TabRow(selectedTabIndex = tabIndex.value!!) {
                    viewModel.tabs.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    text = title,
                                    letterSpacing = 1.sp
                                )
                            },
                            selected = tabIndex.value!! == index,
                            onClick = { viewModel.updateTabIndex(index) }
                        )
                    }
                }
                when (tabIndex.value) {
                    0 -> ChildNutritionScreen(navController)
                    1 -> ChildStuntingScreen(navController)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChildMonitoringMainScreenPreview() {
    ChildMonitoringMainScreen(
        navController = rememberNavController()
    )
}