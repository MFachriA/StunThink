package com.projectAnya.stunthink.presentation.screen.main.profile.edit

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
import androidx.compose.runtime.derivedStateOf
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
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.projectAnya.stunthink.R
import com.projectAnya.stunthink.data.remote.dto.user.UserDto
import com.projectAnya.stunthink.presentation.component.appbar.BackButtonAppBar
import com.projectAnya.stunthink.presentation.ui.theme.StunThinkTheme
import com.projectAnya.stunthink.utils.DateUtils.formatDateToIndonesianDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditProfileScreen(
    navController: NavController,
    user: UserDto?,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    StunThinkTheme {
        val context = LocalContext.current
        LaunchedEffect(key1 = context) {
            user?.let {
                viewModel.onEvent(EditProfileFormEvent.NameChanged(user.namaLengkap))
                viewModel.onEvent(EditProfileFormEvent.GenderChanged(user.jenisKelamin))
                viewModel.onEvent(EditProfileFormEvent.DateChanged(user.tanggalLahir))
                viewModel.onEvent(EditProfileFormEvent.AddressChanged(user.tempatLahir))
            }

            viewModel.validationEvents.collect { event ->
                when (event) {
                    is EditProfileViewModel.ValidationEvent.Success -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                        navController.popBackStack()
                    }
                    is EditProfileViewModel.ValidationEvent.Failed -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = "Ubah Data Akun",
                    navigationOnClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                ) {
                    user?.let {
                        Content(user)
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Content(
    user: UserDto,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val formState = viewModel.formState
    val submitState = viewModel.submitState.value

    val genderOptions = listOf(
        stringResource(id = R.string.male),
        stringResource(id = R.string.female)
    )

    var selectedGenderOption by remember { mutableStateOf(
        if (user.jenisKelamin == "M") {
            genderOptions[0]
        } else {
            genderOptions[1]
        }
    ) }

    var _isFieldChanged by remember { mutableStateOf (false) }
    val isFieldChanged = remember { derivedStateOf { _isFieldChanged } }

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
                value = formState.name,
                onValueChange = {
                    viewModel.onEvent(EditProfileFormEvent.NameChanged(it))
                    _isFieldChanged = true
                },
                modifier = Modifier.fillMaxWidth(),
                isError = formState.nameError != null,
                label = { Text(text = stringResource(id = R.string.full_name)) },
                placeholder = { Text(text = stringResource(id = R.string.input_full_name)) },
                supportingText = formState.nameError?.let {
                    { Text(text = formState.nameError) }
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
                                _isFieldChanged = true
                                selectedGenderOption = text
                                viewModel.onEvent(
                                    EditProfileFormEvent.GenderChanged(text)
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
                value = formatDateToIndonesianDate(formState.date),
                onValueChange = {
                    viewModel.onEvent(EditProfileFormEvent.DateChanged(it))
                    _isFieldChanged = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                isError = formState.dateError != null,
                label = { Text(text = stringResource(id = R.string.date_of_birth)) },
                placeholder = { Text(text = stringResource(id = R.string.input_date_of_birth)) },
                supportingText = formState.dateError?.let {
                    { Text(text = formState.dateError) }
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
                value = formState.address,
                onValueChange = {
                    viewModel.onEvent(EditProfileFormEvent.AddressChanged(it))
                    _isFieldChanged = true
                },
                modifier = Modifier.fillMaxWidth(),
                isError = formState.addressError != null,
                label = { Text(text = stringResource(id = R.string.address)) },
                placeholder = { Text(text = stringResource(id = R.string.input_address)) },
                supportingText = formState.addressError?.let {
                    { Text(text = formState.addressError) }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
            )
            Button(
                onClick = { viewModel.onEvent(EditProfileFormEvent.Submit) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                enabled = isFieldChanged.value
            ) {
                Text(text = "Ubah Data")
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
            viewModel.onEvent(EditProfileFormEvent.DateChanged(date.toString()))
            _isFieldChanged = true
            focusManager.clearFocus()
        },
        properties = DialogProperties()
    )

    if (submitState.isLoading) {
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
fun EditProfileScreenPreview() {
    com.projectAnya.stunthink.presentation.screen.monitoring.child.edit.EditChildScreen(
        navController = rememberNavController(),
        child = null
    )
}