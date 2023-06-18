package com.example.stunthink.presentation.screen.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stunthink.domain.use_case.user.DeleteUserTokenUseCase
import com.example.stunthink.domain.use_case.user.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    getUserTokenUseCase: GetUserTokenUseCase,
    private val deleteUserTokenUseCase: DeleteUserTokenUseCase
): ViewModel() {
    init {
        deleteUserToken()
    }

    private fun deleteUserToken() {
        viewModelScope.launch {
            deleteUserTokenUseCase.execute()
        }
    }
}