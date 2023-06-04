package com.example.stunthink.presentation.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stunthink.domain.use_case.user.DeleteUserTokenUseCase
import com.example.stunthink.domain.use_case.user.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getUserTokenUseCase: GetUserTokenUseCase,
    private val deleteUserTokenUseCase: DeleteUserTokenUseCase
): ViewModel() {
    val userTokenFlow: Flow<String?> = getUserTokenUseCase.execute()

    fun deleteUserToken() {
        viewModelScope.launch {
            deleteUserTokenUseCase.execute()
        }
    }
}