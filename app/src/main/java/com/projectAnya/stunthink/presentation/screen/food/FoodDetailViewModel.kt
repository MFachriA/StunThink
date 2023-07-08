package com.projectAnya.stunthink.presentation.screen.food

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.monitoring.food.DeleteFoodUseCase
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
class FoodDetailViewModel @Inject constructor(
    private val deleteFoodUseCase: DeleteFoodUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase
): ViewModel() {
    private val _deleteDialogState = MutableStateFlow(false)
    val deleteDialogState: StateFlow<Boolean> = _deleteDialogState.asStateFlow()

    private val _userTokenState = MutableStateFlow<String?>(null)
    val userTokenState: StateFlow<String?> = _userTokenState.asStateFlow()

    private val _state = mutableStateOf(FoodDetailState())
    val submitState: State<FoodDetailState> = _state

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

    fun uploadDialogState(value: Boolean) {
        _deleteDialogState.value = value
    }

    fun submitChildFood(foodId: String) {
        _userTokenState.value?.let { token ->
            deleteFoodUseCase.invoke(
                token = token,
                id = foodId
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = FoodDetailState(
                            message = result.message ?: "Register berhasil!"
                        )
                        validationEventChannel.send(ValidationEvent.Success(_state.value.message))
                    }
                    is Resource.Error -> {
                        _state.value = FoodDetailState(
                            message = result.message ?: "Terjadi kesalahan tak terduga"
                        )
                        validationEventChannel.send(ValidationEvent.Failed(_state.value.message))
                    }
                    is Resource.Loading -> {
                        _state.value = FoodDetailState(isLoading = true)
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