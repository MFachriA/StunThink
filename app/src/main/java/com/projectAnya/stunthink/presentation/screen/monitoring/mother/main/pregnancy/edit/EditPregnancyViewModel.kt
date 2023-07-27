package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.model.pregnancy.Pregnancy
import com.projectAnya.stunthink.domain.model.pregnancy.PregnancyType
import com.projectAnya.stunthink.domain.use_case.monitoring.mother.EditMotherPregnancyUseCase
import com.projectAnya.stunthink.domain.use_case.user.GetUserTokenUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateDateUseCase
import com.projectAnya.stunthink.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPregnancyViewModel @Inject constructor(
    private val editMotherPregnancyUseCase: EditMotherPregnancyUseCase,
    private val validateDateUseCase: ValidateDateUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase
    ): ViewModel() {

    private val tokenState = MutableStateFlow<String?>(null)
    private val pregnancyState = MutableStateFlow<Pregnancy?>(null)

    var formState by mutableStateOf(EditPregnancyFormState())

    private val _state = mutableStateOf(EditPregnancySubmitState())
    val submitState: State<EditPregnancySubmitState> = _state

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

    fun onEvent(event: EditPregnancyFormEvent) {
        when(event) {
            is EditPregnancyFormEvent.DateChanged -> {
                formState = formState.copy(date = event.height)
            }
            is EditPregnancyFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun setPregnancy(pregnancy: Pregnancy?) {
        pregnancyState.value = pregnancy
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
            pregnancyState.value?.let { pregnancy ->
                editMotherPregnancyUseCase.invoke(
                    token = token,
                    id = pregnancy.id,
                    pregnantDate = DateUtils.formatDateTimeToDate(pregnancy.pregnantDate),
                    pregnancyType = PregnancyType.BORN.displayName,
                    birthDate = formState.date
                ).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = EditPregnancySubmitState(
                                message = result.message ?: "Berhasil mengubah data kehamilan"
                            )
                            if (result.success == true) {
                                validationEventChannel.send(ValidationEvent.Success(_state.value.message))
                            }
                        }
                        is Resource.Error -> {
                            _state.value = EditPregnancySubmitState(
                                message = result.message ?: "Terjadi kesalahan tak terduga"
                            )
                            validationEventChannel.send(ValidationEvent.Failed(_state.value.message))
                        }
                        is Resource.Loading -> {
                            _state.value = EditPregnancySubmitState(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)

            }
        }
    }

    sealed class ValidationEvent {
        data class Success(val message: String): ValidationEvent()
        data class Failed(val message: String): ValidationEvent()
    }
}