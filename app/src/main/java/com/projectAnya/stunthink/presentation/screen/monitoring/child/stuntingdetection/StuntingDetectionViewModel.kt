package com.projectAnya.stunthink.presentation.screen.monitoring.child.stuntingdetection

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.monitoring.child.AddChildStuntingUseCase
import com.projectAnya.stunthink.domain.use_case.user.GetUserTokenUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateDropDownUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateHeightUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StuntingDetectionViewModel @Inject constructor(
    private val addChildStuntingUseCase: AddChildStuntingUseCase,
    private val validateHeightUseCase: ValidateHeightUseCase,
    private val validateDropDownUseCase: ValidateDropDownUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase
    ): ViewModel() {

    private val tokenState = MutableStateFlow<String?>(null)
    private val childIdState = MutableStateFlow<String?>(null)

    var formState by mutableStateOf(ChildRegisterFormState())

    private val _state = mutableStateOf(ChildRegisterSubmitState())
    val submitState: State<ChildRegisterSubmitState> = _state

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

    fun setChildId(token: String?) {
        childIdState.value = token
    }

    fun onEvent(event: StuntingDetectionFormEvent) {
        when(event) {
            is StuntingDetectionFormEvent.HeightChanged -> {
                formState = formState.copy(height = event.height)
            }
            is StuntingDetectionFormEvent.SupineChanged -> {
                formState = formState.copy(supine = event.isSupine)
            }
            is StuntingDetectionFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val heightResult = validateHeightUseCase.execute(formState.height)
        val supineResult = validateDropDownUseCase.execute(formState.supine)

        val hasError = listOf(
            heightResult,
            supineResult
        ).any { !it.successful }

        formState = formState.copy(
            heightError = heightResult.errorMessage,
            supineError = supineResult.errorMessage,
        )

        if (hasError) { return }

        onSubmit()
    }

    private fun onSubmit() {
        tokenState.value?.let { token ->
            childIdState.value?.let { id ->
                addChildStuntingUseCase.invoke(
                    token = token,
                    id = id,
                    height = formState.height.toInt(),
                    isSupine = isSupineToBoolean(formState.supine)
                ).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = ChildRegisterSubmitState(
                                message = result.message ?: "Berhasil menambahkan data stunting anak!"
                            )
                            if (result.success == true) {
                                validationEventChannel.send(ValidationEvent.Success(_state.value.message))
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
        }
    }

    private fun isSupineToBoolean(isSupine: String): Boolean {
        return isSupine == "Terlentang"
    }

    sealed class ValidationEvent {
        data class Success(val message: String): ValidationEvent()
        data class Failed(val message: String): ValidationEvent()
    }
}