package com.projectAnya.stunthink.presentation.screen.monitoring.child.fooddetection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projectAnya.stunthink.domain.use_case.user.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildFoodDetectionViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase
): ViewModel() {
    private val _userTokenState = MutableStateFlow<String?>(null)
    val userTokenState: StateFlow<String?> = _userTokenState.asStateFlow()

    private val _childIdState = MutableStateFlow<String?>(null)
    val childIdState: StateFlow<String?> = _childIdState.asStateFlow()

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

    fun setChildId(id: String?) {
        _childIdState.value = id
    }
}