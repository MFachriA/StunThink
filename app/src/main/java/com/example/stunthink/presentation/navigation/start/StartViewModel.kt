package com.example.stunthink.presentation.navigation.start

import androidx.lifecycle.ViewModel
import com.example.stunthink.domain.use_case.user.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    getUserTokenUseCase: GetUserTokenUseCase
): ViewModel() {
    val userTokenFlow: Flow<String?> = getUserTokenUseCase.execute()
}