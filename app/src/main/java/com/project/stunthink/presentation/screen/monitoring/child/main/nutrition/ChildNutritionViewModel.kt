package com.project.stunthink.presentation.screen.monitoring.child.main.nutrition

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.stunthink.domain.use_case.monitoring.child.GetChildNutritionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChildNutritionViewModel @Inject constructor(
    private val getChildNutritionUseCase: GetChildNutritionUseCase
): ViewModel() {
    private val _state = mutableStateOf(ChildNutritionListState())
    val state: State<ChildNutritionListState> = _state

    fun getNutritions(token: String, id: String) {
        getChildNutritionUseCase(token = token, id = id).onEach { result ->
            when (result) {
                is com.project.stunthink.domain.common.Resource.Success -> {
                    _state.value = ChildNutritionListState(nutritions = result.data ?: emptyList())
                }
                is com.project.stunthink.domain.common.Resource.Error -> {
                    _state.value = ChildNutritionListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is com.project.stunthink.domain.common.Resource.Loading -> {
                    _state.value = ChildNutritionListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}