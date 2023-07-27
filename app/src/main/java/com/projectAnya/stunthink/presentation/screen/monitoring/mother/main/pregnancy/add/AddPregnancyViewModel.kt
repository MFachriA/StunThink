package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy.add

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.model.pregnancy.PregnancyType
import com.projectAnya.stunthink.domain.use_case.monitoring.mother.AddMotherPregnancyUseCase
import com.projectAnya.stunthink.domain.use_case.user.GetUserTokenUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPregnancyViewModel @Inject constructor(
    private val addMotherPregnancyUseCase: AddMotherPregnancyUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase
    ): ViewModel() {

    private val tokenState = MutableStateFlow<String?>(null)

    var formState by mutableStateOf(AddPregnancyFormState())

    private val _state = mutableStateOf(AddPregnancySubmitState())
    val submitState: State<AddPregnancySubmitState> = _state

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

    fun onEvent(event: AddPregnancyFormEvent) {
        when(event) {
            is AddPregnancyFormEvent.DateChanged -> {
                formState = formState.copy(date = event.height)
            }
            is AddPregnancyFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val dateResult = validateDateUseCase.execute(formState.date)

        val hasError = listOf(
            dateResult,
        ).any { !it.successful }

        formState = formState.copy(dateError = dateResult.errorMessage,)

        if (hasError) { return }

        onSubmit()
    }

    private fun onSubmit() {
        tokenState.value?.let { token ->
            addMotherPregnancyUseCase.invoke(
                token = token,
                pregnantDate = formState.date,
                pregnancyType = PregnancyType.CONCEIVE.displayName
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = AddPregnancySubmitState(
                            message = result.message ?: "Berhasil menambahkan data stunting anak!"
                        )
                        if (result.success == true) {
                            validationEventChannel.send(ValidationEvent.Success(_state.value.message))
                        }
                    }
                    is Resource.Error -> {
                        _state.value = AddPregnancySubmitState(
                            message = result.message ?: "Terjadi kesalahan tak terduga"
                        )
                        validationEventChannel.send(ValidationEvent.Failed(_state.value.message))
                    }
                    is Resource.Loading -> {
                        _state.value = AddPregnancySubmitState(isLoading = true)
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