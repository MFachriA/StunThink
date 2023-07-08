package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.monitoring.mother.GetMotherNutritionUseCase
import com.projectAnya.stunthink.domain.use_case.user.GetUserTokenUseCase
import com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition.ChildNutritionListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MotherMonitoringMainViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val getMotherNutritionUseCase: GetMotherNutritionUseCase

): ViewModel() {
    private val _userTokenState = MutableStateFlow<String?>(null)
    val userTokenState: StateFlow<String?> = _userTokenState.asStateFlow()

    private val _state = mutableStateOf(ChildNutritionListState())
    val state: State<ChildNutritionListState> = _state

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

    fun getNutritions(token: String) {
        getMotherNutritionUseCase(token = token).onEach { result ->
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