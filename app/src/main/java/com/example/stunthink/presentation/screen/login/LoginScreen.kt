package com.example.stunthink.presentation.screen.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stunthink.R
import com.example.stunthink.presentation.component.appbar.BackButtonAppBar
import com.example.stunthink.presentation.navigation.ScreenRoute
import com.example.stunthink.presentation.ui.theme.StunThinkTheme
import com.example.stunthink.presentation.ui.theme.Typography

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    StunThinkTheme {
        val context = LocalContext.current
        LaunchedEffect(key1 = context) {
            loginViewModel.validationEvents.collect { event ->
                when (event) {
                    is LoginViewModel.ValidationEvent.Success -> {
                        navController.navigate(route = ScreenRoute.Main.route) {
                            popUpTo(ScreenRoute.Welcome.route) { inclusive = true }
                        }
                        Toast.makeText(context, R.string.login_success_message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        Scaffold(
            topBar = {
                BackButtonAppBar(
                    title = stringResource(id = R.string.login_page_title),
                    navigationOnClick = { navController.popBackStack() }
                )
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    Content(navController)
                }
            }
        )
    }
}

@Composable
private fun Content(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val loginState = loginViewModel.formState
    val loginSubmitState = loginViewModel.submitState.value

    var passwordVisibility by rememberSaveable { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_screen_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = stringResource(id = R.string.login_title), style = Typography.headlineSmall)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = stringResource(id = R.string.login_message),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = loginState.email,
                    onValueChange = {
                        loginViewModel.onEvent(LoginFormEvent.EmailChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = loginState.emailError != null,
                    label = { Text(text = stringResource(id = R.string.email)) },
                    placeholder = { Text(text = stringResource(id = R.string.input_email)) },
                    supportingText = loginState.emailError?.let {
                        { Text(text = loginState.emailError) }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    maxLines = 1,
                )
                OutlinedTextField(
                    value = loginState.password,
                    onValueChange = {
                        loginViewModel.onEvent(LoginFormEvent.PasswordChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = loginState.passwordError != null,
                    label = { Text(text = stringResource(id = R.string.password)) },
                    placeholder = { Text(text = stringResource(id = R.string.input_password)) },
                    supportingText = loginState.passwordError?.let {
                        { Text(text = loginState.passwordError) }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
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
                    },
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.padding(12.dp))

            Button(
                onClick = {
                    loginViewModel.onEvent(LoginFormEvent.Submit)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.login))
            }
        }
    }

    if (loginSubmitState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController()
    )
}
