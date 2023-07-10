package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.data.remote.dto.nutrition.FoodDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionDetailDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionDto
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.component.card.NutritionCard
import com.projectAnya.stunthink.presentation.component.card.NutritionSummaryCard
import com.projectAnya.stunthink.presentation.component.content.BaseContent
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.presentation.ui.theme.Typography
import com.projectAnya.stunthink.utils.DateUtils

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

    val dateState = viewModel.dateState.observeAsState()
    val date = dateState.value

    val nutritionStatusState = viewModel.nutritionStatusState.value
    val nutritionStandardState = viewModel.nutritionStandardState.value
    val nutritionListState = viewModel.nutritionListState.value

    val calendarState = rememberUseCaseState()

    LaunchedEffect(key1 = context, key2 = date) {
        userToken?.let { token ->
            viewModel.getNutritionStatus(token, date)
            viewModel.getNutritionStandard(token)
            viewModel.getNutritions(token, date)
        }
    }

    StunThinkTheme {
        Scaffold(
            topBar = {
                BackButtonAppBar(
                    modifier = Modifier,
                    title = stringResource(id = R.string.mother_monitoring_title),
                    navigationOnClick = { navController.popBackStack() },
                    actions = { }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(
                            ScreenRoute.FoodDetection.route
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add",
                        tint = Color.White,
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                Column {
                    Surface(
                        modifier = Modifier.clickable { calendarState.show() },
                        shape = RectangleShape,
                        border = BorderStroke(1.dp, Color.Gray),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Tanggal",
                                style = Typography.bodyLarge
                            )
                            Text(
                                text =
                                if (date != null) DateUtils.formatDateToIndonesianDate(date)
                                else "Hari Ini",
                                style = Typography.titleMedium
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        MotherMonitoringNutritionSummary(
                            navController = navController,
                            startNutrition = nutritionStatusState.nutritionStatus?._sum,
                            targetNutrition = nutritionStandardState.nutritionStandard?.standarGiziDetail
                        )
                        MotherMonitoringNutritionList(
                            nutritionList = nutritionListState.nutritions,
                            navController = navController
                        )
                    }
                }

                CalendarDialog(
                    state = calendarState,
                    config = CalendarConfig(
                        yearSelection = true,
                        monthSelection = true,
                        style = CalendarStyle.MONTH
                    ),
                    selection = CalendarSelection.Date { date ->
                        viewModel.updateDate(date.toString())
                    },
                    properties = DialogProperties()
                )
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

@Composable
fun MotherMonitoringNutritionSummary(
    modifier: Modifier = Modifier,
    navController: NavController,
    startNutrition: NutritionDetailDto?,
    targetNutrition: NutritionDetailDto?,
) {
    BaseContent(
        modifier = modifier
            .fillMaxWidth(),
        title = "Total Nutrisi Hari Ini"
    ) {
        NutritionSummaryCard(
            modifier = Modifier.clickable {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "nutritionStart",
                    value = startNutrition
                )
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "nutritionTarget",
                    value = targetNutrition
                )
                navController.navigate(ScreenRoute.NutritionDetail.route)
            },
            startNutrition = startNutrition,
            targetNutrition = targetNutrition
        )
    }
}

@Composable
fun MotherMonitoringNutritionList(
    modifier: Modifier = Modifier,
    nutritionList: List<NutritionDto>,
    navController: NavController
) {
    BaseContent(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp),
        title = "Daftar Makanan"
    ) {
        if (nutritionList.isEmpty()) {
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
                    text = "Belum ada data makanan",
                    style = Typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(items = nutritionList, itemContent = { nutrition ->
                    NutritionCard(
                        image = nutrition.foodUrl,
                        name = nutrition.namaMakanan,
                        date = nutrition.timastamp
                    ) {
                        val food = FoodDto(
                            dataGizi = nutrition,
                            image = ""
                        )
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "food",
                            value = food
                        )
                        navController.navigate(
                            ScreenRoute.FoodDetail.route
                        )
                    }
                })
            }
        }
    }
}
