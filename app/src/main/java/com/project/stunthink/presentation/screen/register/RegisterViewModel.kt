package com.project.stunthink.presentation.screen.register

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.stunthink.domain.use_case.register.RegisterUseCase
import com.project.stunthink.domain.use_case.validate.ValidateAddressUseCase
import com.project.stunthink.domain.use_case.validate.ValidateConfirmationPasswordUseCase
import com.project.stunthink.domain.use_case.validate.ValidateDateUseCase
import com.project.stunthink.domain.use_case.validate.ValidateEmailUseCase
import com.project.stunthink.domain.use_case.validate.ValidateNameUseCase
import com.project.stunthink.domain.use_case.validate.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateConfirmationPasswordUseCase: ValidateConfirmationPasswordUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validateAddressUseCase: ValidateAddressUseCase,
    private val registerUseCase: RegisterUseCase

): ViewModel() {

    var formState by mutableStateOf(RegisterFormState())

    private val _state = mutableStateOf(RegisterSubmitState())
    val submitState: State<RegisterSubmitState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegisterFormEvent) {
        when(event) {
            is RegisterFormEvent.NameChanged -> {
                formState = formState.copy(name = event.name)
            }
            is RegisterFormEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }
            is RegisterFormEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }
            is RegisterFormEvent.ConfirmationPasswordChanged -> {
                formState = formState.copy(confirmationPassword = event.confirmationPassword)
            }
            is RegisterFormEvent.GenderChanged -> {
                formState = if (event.gender == "Laki - Laki") {
                    formState.copy(gender = "M")
                } else
                    formState.copy(gender = "F")
            }
            is RegisterFormEvent.DateChanged -> {
                formState = formState.copy(date = event.date)
            }
            is RegisterFormEvent.AddressChanged -> {
                formState = formState.copy(address = event.address)
            }
            is RegisterFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val nameResult = validateNameUseCase.execute(formState.name)
        val emailResult = validateEmailUseCase.execute(formState.email)
        val passwordResult = validatePasswordUseCase.execute(formState.password)
        val confirmationPasswordResult = validateConfirmationPasswordUseCase.execute(
            formState.password,
            formState.confirmationPassword
        )
        val dateResult = validateDateUseCase.execute(formState.date)
        val addressResult = validateAddressUseCase.execute(formState.address)

        val hasError = listOf(
            nameResult,
            emailResult,
            passwordResult,
            confirmationPasswordResult,
            dateResult,
            addressResult
        ).any { !it.successful }

        formState = formState.copy(
            nameError = nameResult.errorMessage,
            emailError = emailResult.errorMessage,
            passwordError = passwordResult.errorMessage,
            confirmationPasswordError = confirmationPasswordResult.errorMessage,
            dateError = dateResult.errorMessage,
            addressError = addressResult.errorMessage
        )

        if (hasError) { return }

        onSubmit()
    }

    private fun onSubmit() {
        registerUseCase.invoke(
            name = formState.name,
            email = formState.email,
            password = formState.password,
            confirmationPassword = formState.confirmationPassword,
            gender = formState.gender,
            date = formState.date,
            address = formState.address
        ).onEach { result ->
            when (result) {
                is com.project.stunthink.domain.common.Resource.Success -> {
                    _state.value = RegisterSubmitState(
                        message = result.message ?: "Register berhasil!"
                    )
                    validationEventChannel.send(ValidationEvent.Success)
                }
                is com.project.stunthink.domain.common.Resource.Error -> {
                    _state.value = RegisterSubmitState(
                        message = result.message ?: "Terjadi kesalahan tak terduga"
                    )
                }
                is com.project.stunthink.domain.common.Resource.Loading -> {
                    _state.value = RegisterSubmitState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}