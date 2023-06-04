package com.example.stunthink.presentation.screen.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stunthink.common.Resource
import com.example.stunthink.domain.use_case.login.LoginUseCase
import com.example.stunthink.domain.use_case.user.SaveUserTokenUseCase
import com.example.stunthink.domain.use_case.validate.ValidateEmailUseCase
import com.example.stunthink.domain.use_case.validate.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val saveUserTokenUseCase: SaveUserTokenUseCase,
    private val loginUseCase: LoginUseCase
): ViewModel() {

    var formState by mutableStateOf(LoginFormState())

    private val _state = mutableStateOf(LoginSubmitState())
    val submitState: State<LoginSubmitState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            saveUserTokenUseCase.execute(token)
        }
    }

    fun onEvent(event: LoginFormEvent) {
        when(event) {
            is LoginFormEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }
            is LoginFormEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }
            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmailUseCase.execute(formState.email)
        val passwordResult = validatePasswordUseCase.execute(formState.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        formState = formState.copy(
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage
        )

        if (hasError) { return }

        onSubmit()
    }

    private fun onSubmit() {
        loginUseCase.invoke(
            email = formState.email, password = formState.password
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = LoginSubmitState(
                        message = result.message ?: "Login berhasil!",
                        token = result.data?.token ?: ""
                    )
                    saveUserToken(result.data?.token ?: "")
                    validationEventChannel.send(ValidationEvent.Success)
                }
                is Resource.Error -> {
                    _state.value = LoginSubmitState(
                        message = result.message ?: "Terjadi kesalahan tak terduga"
                    )
                    formState = formState.copy(
                        emailError = "Pastikan email yang dimasukan benar!",
                        passwordError = "Pastikan password yang dimasukan benar!"
                    )
                }
                is Resource.Loading -> {
                    _state.value = LoginSubmitState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}