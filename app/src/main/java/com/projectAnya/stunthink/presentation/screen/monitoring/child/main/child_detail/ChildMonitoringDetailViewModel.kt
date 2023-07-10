package com.projectAnya.stunthink.presentation.screen.monitoring.child.main.child_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.monitoring.child.DeleteChildUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.child.GetChildDetailUseCase
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
class ChildMonitoringDetailViewModel @Inject constructor(
    private val getChildDetailUseCase: GetChildDetailUseCase,
    private val deleteChildUseCase: DeleteChildUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase
): ViewModel() {
    private val _userTokenState = MutableStateFlow<String?>(null)
    val userTokenState: StateFlow<String?> = _userTokenState.asStateFlow()

    private val _deleteDialogState = MutableStateFlow(false)
    val deleteDialogState: StateFlow<Boolean> = _deleteDialogState.asStateFlow()

    private val _childDetailState = mutableStateOf(ChildMonitoringDetailState())
    val childDetailState: State<ChildMonitoringDetailState> = _childDetailState

    private val _deleteState = mutableStateOf(ChildMonitoringDeleteState())
    val deleteState: State<ChildMonitoringDeleteState> = _deleteState

    private val validationEventChannel = Channel<DeleteValidationEvent>()
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

    fun getChildDetail(id: String){
        _userTokenState.value?.let { token ->
            getChildDetailUseCase.invoke(
                    token = token,
                    id = id,
                ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _childDetailState.value = ChildMonitoringDetailState(childDetail = result.data)
                    }
                    is Resource.Error -> {
                        _childDetailState.value = ChildMonitoringDetailState(
                            message = result.message ?: "An unexpected error occured"
                        )
                    }
                    is Resource.Loading -> {
                        _childDetailState.value = ChildMonitoringDetailState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun uploadDialogState(value: Boolean) {
        _deleteDialogState.value = value
    }

    fun deleteChild(stuntingId: String) {
        _userTokenState.value?.let { token ->
            deleteChildUseCase.invoke(
                token = token,
                id = stuntingId
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _deleteState.value = ChildMonitoringDeleteState(
                            message = result.message ?: "Register berhasil!"
                        )
                        validationEventChannel.send(DeleteValidationEvent.Success(_deleteState.value.message))
                    }
                    is Resource.Error -> {
                        _deleteState.value = ChildMonitoringDeleteState(
                            message = result.message ?: "Terjadi kesalahan tak terduga"
                        )
                        validationEventChannel.send(DeleteValidationEvent.Failed(_deleteState.value.message))
                    }
                    is Resource.Loading -> {
                        _deleteState.value = ChildMonitoringDeleteState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)

        }
    }

    sealed class ChildValidationEvent {
        data class Success(val message: String): ChildValidationEvent()
        data class Failed(val message: String): ChildValidationEvent()
    }

    sealed class DeleteValidationEvent {
        data class Success(val message: String): DeleteValidationEvent()
        data class Failed(val message: String): DeleteValidationEvent()
    }
}