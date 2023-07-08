package com.projectAnya.stunthink.presentation.screen.food.monitoring

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.monitoring.child.AddChildFoodUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.mother.AddMotherFoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MonitoringFoodDetailViewModel @Inject constructor(
    private val addChildFoodUseCase: AddChildFoodUseCase,
    private val addMotherFoodUseCase: AddMotherFoodUseCase
): ViewModel() {
    val foodPortionState = MutableStateFlow(1f)

    private val _state = mutableStateOf(MonitoringFoodDetailState())
    val submitState: State<MonitoringFoodDetailState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun updateFoodPortion(portion: Float) {
        foodPortionState.value = portion
    }

    fun submitChildFood(
        token: String,
        id: String,
        foodPercentage: Float,
        foodId: String,
        foodImageUrl: String?
    ) {
        addChildFoodUseCase.invoke(
            token = token,
            id = id,
            foodPercentage = foodPercentage,
            foodId = foodId,
            foodImageUrl = foodImageUrl
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MonitoringFoodDetailState(
                        message = result.message ?: "Register berhasil!"
                    )
                    validationEventChannel.send(ValidationEvent.Success(_state.value.message))
                }
                is Resource.Error -> {
                    _state.value = MonitoringFoodDetailState(
                        message = result.message ?: "Terjadi kesalahan tak terduga"
                    )
                    validationEventChannel.send(ValidationEvent.Failed(_state.value.message))
                }
                is Resource.Loading -> {
                    _state.value = MonitoringFoodDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun submitMotherFood(
        token: String,
        foodPercentage: Float,
        foodId: String,
        foodImageUrl: String?
    ) {
        addMotherFoodUseCase.invoke(
            token = token,
            foodPercentage = foodPercentage,
            foodId = foodId,
            foodImageUrl = foodImageUrl
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MonitoringFoodDetailState(
                        message = result.message ?: "Register berhasil!"
                    )
                    validationEventChannel.send(ValidationEvent.Success(_state.value.message))
                }
                is Resource.Error -> {
                    _state.value = MonitoringFoodDetailState(
                        message = result.message ?: "Terjadi kesalahan tak terduga"
                    )
                    validationEventChannel.send(ValidationEvent.Failed(_state.value.message))
                }
                is Resource.Loading -> {
                    _state.value = MonitoringFoodDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class ValidationEvent {
        data class Success(val message: String): ValidationEvent()
        data class Failed(val message: String): ValidationEvent()
    }
}