package com.example.stunthink.presentation.screen.monitoring.child.main

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stunthink.R
import com.example.stunthink.presentation.screen.monitoring.child.main.nutrition.ChildNutritionScreen
import com.example.stunthink.presentation.screen.monitoring.child.main.stunting.ChildStuntingScreen
import com.example.stunthink.presentation.ui.theme.StunThinkTheme
import com.example.stunthink.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildMonitoringMainScreen(
    navController: NavController,
    id: String? = null,
    name: String? = null,
    viewModel: ChildMonitoringMainViewModel = hiltViewModel()
) {
    val tabIndex = viewModel.tabIndex.observeAsState()

    StunThinkTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = stringResource(id = R.string.child_monitoring_title)
                            )
                            name?.let {
                                Text(
                                    text = name,
                                    style = Typography.labelMedium
                                )
                            }
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
            },
            content = { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    TabRow(selectedTabIndex = tabIndex.value!!) {
                        viewModel.tabs.forEachIndexed { index, title ->
                            Tab(text = { Text(title) },
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
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChildMonitoringMainScreenPreview() {
    ChildMonitoringMainScreen(
        navController = rememberNavController()
    )
}