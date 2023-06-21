package com.example.stunthink.presentation.navigation.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stunthink.domain.use_case.user.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase
): ViewModel() {
    private val _userToken = MutableStateFlow<String?>(null)
    val userToken: StateFlow<String?>
        get() = _userToken

    init {
        fetchUserToken()
    }

    private fun fetchUserToken() {
        viewModelScope.launch {
            val token = getUserTokenUseCase.execute().first()
            _userToken.value = token
        }
    }
}