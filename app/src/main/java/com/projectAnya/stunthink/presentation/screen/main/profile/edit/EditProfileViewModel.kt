package com.projectAnya.stunthink.presentation.screen.main.profile.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.user.EditUserDetailUseCase
import com.projectAnya.stunthink.domain.use_case.user.GetUserTokenUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateAddressUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateDateUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val validateAddressUseCase: ValidateAddressUseCase,
    private val editUserDetailUseCase: EditUserDetailUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase

): ViewModel() {
    private val tokenState = MutableStateFlow<String?>(null)

    var formState by mutableStateOf(EditProfileFormState())

    private val _state = mutableStateOf(EditProfileSubmitState())
    val submitState: State<EditProfileSubmitState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        fetchUserToken()
    }

    private fun fetchUserToken() {
        viewModelScope.launch {
            getUserTokenUseCase.execute().collect { token ->
                tokenState.value = token
            }
        }
    }

    fun onEvent(event: EditProfileFormEvent) {
        when(event) {
            is EditProfileFormEvent.NameChanged -> {
                formState = formState.copy(name = event.name)
            }
            is EditProfileFormEvent.GenderChanged -> {
                formState = if (event.gender == "Laki - Laki") {
                    formState.copy(gender = "M")
                } else
                    formState.copy(gender = "F")
            }
            is EditProfileFormEvent.DateChanged -> {
                formState = formState.copy(date = event.date)
            }
            is EditProfileFormEvent.AddressChanged -> {
                formState = formState.copy(address = event.address)
            }
            is EditProfileFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val nameResult = validateNameUseCase.execute(formState.name)
        val dateResult = validateDateUseCase.execute(formState.date)
        val addressResult = validateAddressUseCase.execute(formState.address)

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
        tokenState.value?.let { token ->
            editUserDetailUseCase.invoke(
                token = token,
                name = formState.name,
                gender = formState.gender,
                date = formState.date,
                address = formState.address
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = EditProfileSubmitState(
                            message = result.message ?: "Berhasil mengubah data kehamilan"
                        )
                        if (result.success == true) {
                            validationEventChannel.send(ValidationEvent.Success(_state.value.message))
                        }
                    }
                    is Resource.Error -> {
                        _state.value = EditProfileSubmitState(
                            message = result.message ?: "Terjadi kesalahan tak terduga"
                        )
                        validationEventChannel.send(ValidationEvent.Failed(_state.value.message))
                    }
                    is Resource.Loading -> {
                        _state.value = EditProfileSubmitState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    sealed class ValidationEvent {
        data class Success(val message: String): ValidationEvent()
        data class Failed(val message: String): ValidationEvent()
    }
}