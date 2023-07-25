package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.nutrition.MotherNutritionScreen
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MotherMonitoringMainScreen(
    navController: NavController,
    viewModel: MotherMonitoringMainViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val userTokenState: State<String?> = viewModel.userTokenState.collectAsState()
    val userToken: String? by userTokenState

    val tabIndex = viewModel.tabIndex.observeAsState()

    StunThinkTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.mother_monitoring_title)
                        )
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
                    }
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
                            unselectedContentColor = Color.DarkGray,
                            onClick = { viewModel.updateTabIndex(index) }
                        )
                    }
                }
                when (tabIndex.value) {
                    0 -> MotherNutritionScreen(navController, viewModel)
                    1 -> MotherNutritionScreen(navController, viewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MotherMonitoringMainScreenPreview() {
    MotherMonitoringMainScreen(
        navController = rememberNavController()
    )
}
