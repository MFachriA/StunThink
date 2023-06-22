package com.project.stunthink.presentation.screen.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.stunthink.domain.use_case.user.DeleteUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val deleteUserTokenUseCase: DeleteUserTokenUseCase
): ViewModel() {
    fun deleteUserToken() {
        viewModelScope.launch {
            deleteUserTokenUseCase.execute()
        }
    }
}