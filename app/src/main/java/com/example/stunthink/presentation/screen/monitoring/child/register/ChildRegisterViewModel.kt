package com.example.stunthink.presentation.screen.monitoring.child.register

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stunthink.domain.common.Resource
import com.example.stunthink.domain.use_case.child_register.ChildRegisterUseCase
import com.example.stunthink.domain.use_case.user.GetUserTokenUseCase
import com.example.stunthink.domain.use_case.validate.ValidateDateUseCase
import com.example.stunthink.domain.use_case.validate.ValidateNameUseCase
import com.example.stunthink.domain.use_case.validate.ValidatePlaceOfBirthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildRegisterViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val childRegisterUseCase: ChildRegisterUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validatePlaceOfBirthUseCase: ValidatePlaceOfBirthUseCase,
    ): ViewModel() {

    private val tokenState = mutableStateOf("")

    var formState by mutableStateOf(ChildRegisterFormState())

    private val _state = mutableStateOf(ChildRegisterSubmitState())
    val submitState: State<ChildRegisterSubmitState> = _state
    private val validationEventChannel = Channel<ValidationEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        observeUserToken()
    }

    private fun observeUserToken() {
        viewModelScope.launch {
            getUserTokenUseCase.execute().collect { token ->
                if (token != null) {
                    tokenState.value = token
                }
            }
        }
    }

    fun onEvent(event: ChildRegisterFormEvent) {
        when(event) {
            is ChildRegisterFormEvent.NameChanged -> {
                formState = formState.copy(name = event.name)
            }
            is ChildRegisterFormEvent.GenderChanged -> {
                formState = if (event.gender == "Laki - Laki") {
                    formState.copy(gender = "M")
                } else
                    formState.copy(gender = "F")
            }
            is ChildRegisterFormEvent.DateChanged -> {
                formState = formState.copy(date = event.date)
            }
            is ChildRegisterFormEvent.AddressChanged -> {
                formState = formState.copy(address = event.address)
            }
            is ChildRegisterFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val nameResult = validateNameUseCase.execute(formState.name)
        val dateResult = validateDateUseCase.execute(formState.date)
        val addressResult = validatePlaceOfBirthUseCase.execute(formState.address)

        val hasError = listOf(
            nameResult,
            dateResult,
            addressResult
        ).any { !it.successful }

        formState = formState.copy(
            nameError = nameResult.errorMessage,
            dateError = dateResult.errorMessage,
            addressError = addressResult.errorMessage
        )

        if (hasError) { return }

        onSubmit()
    }

    private fun onSubmit() {
        childRegisterUseCase.invoke(
            token = tokenState.value,
            name = formState.name,
            gender = formState.gender,
            date = formState.date,
            address = formState.address
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ChildRegisterSubmitState(
                        message = result.message ?: "Register berhasil!"
                    )
                    if (result.success == true) {
                        validationEventChannel.send(ValidationEvent.Success(_state.value.message))
                    } else if (result.success == false) {
                        validationEventChannel.send(ValidationEvent.Failed(_state.value.message))
                    }
                }
                is Resource.Error -> {
                    _state.value = ChildRegisterSubmitState(
                        message = result.message ?: "Terjadi kesalahan tak terduga"
                    )
                    validationEventChannel.send(ValidationEvent.Failed(_state.value.message))
                }
                is Resource.Loading -> {
                    _state.value = ChildRegisterSubmitState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class ValidationEvent {
        data class Success(val message: String): ValidationEvent()
        data class Failed(val message: String): ValidationEvent()
    }
}