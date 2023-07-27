package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy.edit

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
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
import com.projectAnya.stunthink.domain.model.pregnancy.Pregnancy
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.navigation.ScreenRoute
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.utils.DateUtils

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditPregnancyScreen(
    navController: NavController,
    pregnancy: Pregnancy?
) {
    StunThinkTheme {
        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = "Ubah Data Kehamilan",
                    navigationOnClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    Content(navController, pregnancy)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Content(
    navController: NavController,
    pregnancy: Pregnancy?,
    viewModel: EditPregnancyViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val calendarState = rememberUseCaseState()

    LaunchedEffect(key1 = context) {
        pregnancy?.let {
            viewModel.setPregnancy(pregnancy)
        }

        viewModel.validationEvents.collect { event ->
            when (event) {
                is EditPregnancyViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    navController.navigate(route = ScreenRoute.MotherMonitoringMain.route) {
                        popUpTo(ScreenRoute.MotherMonitoringMain.route) { inclusive = true }
                    }
                }
                is EditPregnancyViewModel.ValidationEvent.Failed -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val pregnancyFormState = viewModel.formState
    val pregnancySubmitState = viewModel.submitState.value

    Image(
        painter = painterResource(id = R.drawable.register_screen_background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = DateUtils.formatDateToIndonesianDate(pregnancyFormState.date),
                    onValueChange = {
                        viewModel.onEvent(EditPregnancyFormEvent.DateChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    isError = pregnancyFormState.dateError != null,
                    label = { Text(text = "Tanggal Kelahiran") },
                    placeholder = { Text(text = "Masukkan tanggal kelahiran") },
                    supportingText =
                    pregnancyFormState.dateError?.let {
                        { Text(text = pregnancyFormState.dateError) }
                    },
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        calendarState.show()
                                    }
                                }
                            }
                        },
                    maxLines = 1
                )
                Button(
                    onClick = { viewModel.onEvent(EditPregnancyFormEvent.Submit) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                ) {
                    Text(text = "Selesai")
                }
            }
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
            viewModel.onEvent(EditPregnancyFormEvent.DateChanged(date.toString()))
            focusManager.clearFocus()
        },
        properties = DialogProperties()
    )

    if (pregnancySubmitState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.Center)
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun StuntingDetectionScreenPreview() {
    EditPregnancyScreen(
        navController = rememberNavController(),
        pregnancy = null
    )
}