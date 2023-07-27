package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.monitoring.mother.DeleteMotherPregnancyUseCase
import com.projectAnya.stunthink.domain.use_case.user.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PregnancyDetailViewModel @Inject constructor(
    private val deletePregnancyUseCase: DeleteMotherPregnancyUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase
): ViewModel() {
    private val _deleteDialogState = MutableStateFlow(false)
    val deleteDialogState: StateFlow<Boolean> = _deleteDialogState.asStateFlow()

    private val _updateDialogState = MutableStateFlow(false)
    val updateDialogState: StateFlow<Boolean> = _updateDialogState.asStateFlow()

    private val _userTokenState = MutableStateFlow<String?>(null)
    val userTokenState: StateFlow<String?> = _userTokenState.asStateFlow()

    private val _state = mutableStateOf(PregnancyDetailState())
    val submitState: State<PregnancyDetailState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        fetchUserToken()
    }

    private fun fetchUserToken() {
        viewModelScope.launch {
            getUserTokenUseCase.execute().collect { token ->
                _userTokenState.value = token
            }
        }
    }

    fun changeDeleteDialogState(value: Boolean) {
        _deleteDialogState.value = value
    }

    fun changeUpdateDialogState(value: Boolean) {
        _updateDialogState.value = value
    }

    fun deleteStunting(stuntingId: String) {
        _userTokenState.value?.let { token ->
            deletePregnancyUseCase.invoke(
                token = token,
                id = stuntingId
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = PregnancyDetailState(
                            message = result.message ?: "Berhasil hapus data!"
                        )
                        validationEventChannel.send(ValidationEvent.Success(_state.value.message))
                    }
                    is Resource.Error -> {
                        _state.value = PregnancyDetailState(
                            message = result.message ?: "Terjadi kesalahan tak terduga"
                        )
                        validationEventChannel.send(ValidationEvent.Failed(_state.value.message))
                    }
                    is Resource.Loading -> {
                        _state.value = PregnancyDetailState(isLoading = true)
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