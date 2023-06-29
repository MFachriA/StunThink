package com.projectAnya.stunthink.presentation.screen.monitoring.child.main.stunting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.use_case.monitoring.child.GetChildStuntingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChildStuntingViewModel @Inject constructor(
    private val getChildStuntingUseCase: GetChildStuntingUseCase
): ViewModel() {
    private val _state = mutableStateOf(ChildStuntingListState())
    val state: State<ChildStuntingListState> = _state

    fun getStuntings(token: String, id: String) {
        getChildStuntingUseCase(token = token, id = id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ChildStuntingListState(stuntings = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = ChildStuntingListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ChildStuntingListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}