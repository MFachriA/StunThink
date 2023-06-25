package com.projectAnya.stunthink.presentation.screen.monitoring.child.register

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.navigation.start.StartViewModel
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.utils.DateUtils.formatDateToIndonesianDate
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChildRegisterScreen(
    navController: NavController,
) {
    StunThinkTheme {
        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = "Daftarkan Data Anak",
                    navigationOnClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    Content(navController)
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
    startViewModel: StartViewModel = hiltViewModel(),
    childRegisterViewModel: ChildRegisterViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val userTokenState: State<String?> = startViewModel.userTokenState.collectAsState()
    val userToken: String? by userTokenState

    LaunchedEffect(key1 = context) {
        userToken?.let { token ->
            childRegisterViewModel.setToken(token)
        }

        childRegisterViewModel.validationEvents.collect { event ->
            when (event) {
                is ChildRegisterViewModel.ValidationEvent.Success -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                }
                is ChildRegisterViewModel.ValidationEvent.Failed -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val childRegisterState = childRegisterViewModel.formState
    val childRegisterSubmitState = childRegisterViewModel.submitState.value

    val genderOptions = listOf(
        stringResource(id = R.string.male),
        stringResource(id = R.string.female)
    )
    var selectedGenderOption by remember { mutableStateOf(genderOptions[0]) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val calendarState = rememberUseCaseState()

    Image(
        painter = painterResource(id = R.drawable.register_screen_background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = childRegisterState.name,
                onValueChange = {
                    childRegisterViewModel.onEvent(ChildRegisterFormEvent.NameChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                isError = childRegisterState.nameError != null,
                label = { Text(text = stringResource(id = R.string.full_name)) },
                placeholder = { Text(text = stringResource(id = R.string.input_full_name)) },
                supportingText = childRegisterState.nameError?.let {
                    { Text(text = childRegisterState.nameError) }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                maxLines = 1
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                genderOptions.forEach { text ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedGenderOption == text,
                            onClick = {
                                selectedGenderOption = text
                                childRegisterViewModel.onEvent(
                                    ChildRegisterFormEvent.GenderChanged(text)
                                )
                            }
                        )
                        Text(
                            text = text,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            OutlinedTextField(
                value = formatDateToIndonesianDate(childRegisterState.date),
                onValueChange = {
                    childRegisterViewModel.onEvent(ChildRegisterFormEvent.DateChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                isError = childRegisterState.dateError != null,
                label = { Text(text = stringResource(id = R.string.date_of_birth)) },
                placeholder = { Text(text = stringResource(id = R.string.input_date_of_birth)) },
                supportingText = childRegisterState.dateError?.let {
                    { Text(text = childRegisterState.dateError) }
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
            OutlinedTextField(
                value = childRegisterState.address,
                onValueChange = {
                    childRegisterViewModel.onEvent(ChildRegisterFormEvent.AddressChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                isError = childRegisterState.addressError != null,
                label = { Text(text = "Tempat Lahir") },
                placeholder = { Text(text = "Masukkan Tempat Lahir") },
                supportingText = childRegisterState.addressError?.let {
                    { Text(text = childRegisterState.addressError) }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
            )
            Button(
                onClick = { childRegisterViewModel.onEvent(ChildRegisterFormEvent.Submit) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                Text(text = stringResource(R.string.register))
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
            childRegisterViewModel.onEvent(ChildRegisterFormEvent.DateChanged(date.toString()))
            focusManager.clearFocus()
        },
        properties = DialogProperties()
    )

    if (childRegisterSubmitState.isLoading) {
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
fun SignUpScreenPreview() {
    ChildRegisterScreen(
        navController = rememberNavController()
    )
}