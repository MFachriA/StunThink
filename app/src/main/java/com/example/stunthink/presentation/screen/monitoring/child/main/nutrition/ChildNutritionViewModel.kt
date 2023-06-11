package com.example.stunthink.presentation.screen.monitoring.child.main.nutrition

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stunthink.domain.common.Resource
import com.example.stunthink.domain.use_case.monitoring.child.GetChildNutritionUseCase
import com.example.stunthink.domain.use_case.user.GetUserTokenUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ChildNutritionViewModel @Inject constructor(
    getUserTokenUseCase: GetUserTokenUseCase,
    private val getChildNutritionUseCase: GetChildNutritionUseCase
): ViewModel() {
    private val _state = mutableStateOf(ChildNutritionListState())
    val state: State<ChildNutritionListState> = _state

    val userTokenFlow: Flow<String?> = getUserTokenUseCase.execute()

    fun getNutritions(id: String, token: String) {
        getChildNutritionUseCase(id, token).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ChildNutritionListState(nutritions = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = ChildNutritionListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ChildNutritionListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}