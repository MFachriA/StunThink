package com.example.stunthink.presentation.screen.register

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stunthink.R
import com.example.stunthink.presentation.component.appbar.BackButtonAppBar
import com.example.stunthink.presentation.navigation.ScreenRoute
import com.example.stunthink.presentation.ui.theme.StunThinkTheme
import com.example.stunthink.utils.formatDate
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    StunThinkTheme {
        val context = LocalContext.current
        LaunchedEffect(key1 = context) {
            registerViewModel.validationEvents.collect { event ->
                when (event) {
                    is RegisterViewModel.ValidationEvent.Success -> {
                        navController.navigate(route = ScreenRoute.Main.route) {
                            popUpTo(ScreenRoute.Welcome.route) { inclusive = true }
                        }
                        Toast.makeText(context, R.string.register_success_message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = stringResource(id = R.string.register_page_title),
                    navigationOnClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    Content()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Content(
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val registerState = registerViewModel.formState
    val registerSubmitState = registerViewModel.submitState.value

    val genderOptions = listOf(
        stringResource(id = R.string.male),
        stringResource(id = R.string.female)
    )
    var selectedGenderOption by remember { mutableStateOf(genderOptions[0]) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val calendarState = rememberUseCaseState()

    var passwordVisibility by rememberSaveable { mutableStateOf(true) }
    var confirmationPasswordVisibility by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.register_title))
        Spacer(modifier = Modifier.padding(12.dp))
        Text(text = stringResource(id = R.string.register_message))
        Spacer(modifier = Modifier.padding(24.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = registerState.name,
                onValueChange = {
                    registerViewModel.onEvent(RegisterFormEvent.NameChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                isError = registerState.nameError != null,
                label = { Text(text = stringResource(id = R.string.full_name)) },
                placeholder = { Text(text = stringResource(id = R.string.input_full_name)) },
                supportingText = registerState.nameError?.let {
                    { Text(text = registerState.nameError) }
                }
            )
            OutlinedTextField(
                value = registerState.email,
                onValueChange = {
                    registerViewModel.onEvent(RegisterFormEvent.EmailChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                isError = registerState.emailError != null,
                label = { Text(text = stringResource(id = R.string.email)) },
                placeholder = { Text(text = stringResource(id = R.string.input_email)) },
                supportingText = registerState.emailError?.let {
                    { Text(text = registerState.emailError) }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            OutlinedTextField(
                value = registerState.password,
                onValueChange = {
                    registerViewModel.onEvent(RegisterFormEvent.PasswordChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                isError = registerState.passwordError != null,
                label = { Text(text = stringResource(id = R.string.password)) },
                placeholder = { Text(text = stringResource(id = R.string.input_password)) },
                supportingText = registerState.passwordError?.let {
                    { Text(text = registerState.passwordError) }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation =
                    if (passwordVisibility) PasswordVisualTransformation()
                    else VisualTransformation.None,
                trailingIcon = {
                    val image =
                        if (passwordVisibility) Icons.Filled.VisibilityOff
                        else Icons.Filled.Visibility
                    val description =
                        if (passwordVisibility) "show_password" else "hide_password"

                    IconButton(
                        onClick = { passwordVisibility = !passwordVisibility },
                    ) {
                        Icon(
                            imageVector = image,
                            contentDescription = description,
                            tint = Color.Gray
                        )
                    }
                }
            )
            OutlinedTextField(
                value = registerState.confirmationPassword,
                onValueChange = {
                    registerViewModel.onEvent(RegisterFormEvent.ConfirmationPasswordChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                isError = registerState.confirmationPasswordError != null,
                label = { Text(text = stringResource(id = R.string.confirmation_password)) },
                placeholder = { Text(text = stringResource(id = R.string.input_confirmation_password)) },
                supportingText = registerState.confirmationPasswordError?.let {
                    { Text(text = registerState.confirmationPasswordError) }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation =
                    if (passwordVisibility) PasswordVisualTransformation()
                    else VisualTransformation.None,
                trailingIcon = {
                    val image =
                        if (confirmationPasswordVisibility) Icons.Filled.VisibilityOff
                        else Icons.Filled.Visibility
                    val description =
                        if (confirmationPasswordVisibility) "show_password" else "hide_password"

                    IconButton(
                        onClick = { confirmationPasswordVisibility = !confirmationPasswordVisibility },
                    ) {
                        Icon(
                            imageVector = image,
                            contentDescription = description,
                            tint = Color.Gray
                        )
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                genderOptions.forEach { text ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedGenderOption == text,
                            onClick = {
                                selectedGenderOption = text
                                registerViewModel.onEvent(
                                    RegisterFormEvent.GenderChanged(text)
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
                value = formatDate(registerState.date),
                onValueChange = {
                    registerViewModel.onEvent(RegisterFormEvent.DateChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                isError = registerState.dateError != null,
                label = { Text(text = stringResource(id = R.string.date_of_birth)) },
                placeholder = { Text(text = stringResource(id = R.string.input_date_of_birth)) },
                supportingText = registerState.dateError?.let {
                    { Text(text = registerState.dateError) }
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
                    }
            )
            OutlinedTextField(
                value = registerState.address,
                onValueChange = {
                    registerViewModel.onEvent(RegisterFormEvent.AddressChanged(it))
                },
                modifier = Modifier.fillMaxWidth(),
                isError = registerState.addressError != null,
                label = { Text(text = stringResource(id = R.string.address)) },
                placeholder = { Text(text = stringResource(id = R.string.input_address)) },
                supportingText = registerState.addressError?.let {
                    { Text(text = registerState.addressError) }
                }
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = { registerViewModel.onEvent(RegisterFormEvent.Submit) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.register))
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
            registerViewModel.onEvent(RegisterFormEvent.DateChanged(date.toString()))
            focusManager.clearFocus()
        },
        properties = DialogProperties()
    )

    if (registerSubmitState.isLoading) {
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
    RegisterScreen(
        navController = rememberNavController()
    )
}