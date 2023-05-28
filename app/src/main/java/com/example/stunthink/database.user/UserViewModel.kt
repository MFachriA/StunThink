package com.example.stunthink.database.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stunthink.domain.use_case.login.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val userDao: UserDao
): ViewModel() {

    private val _token = userDao.getUserByToken()

    private val _state = MutableStateFlow(UserState())
    val state = combine(_state, _token) { state, token ->
        state.copy(user = token)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserState())

    fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.DeleteUser -> {
                viewModelScope.launch {
                    userDao.deleteUser(event.user)
                }
            }
            UserEvent.SaveUser -> {
                val token = state.value.token

                if (token.isBlank()) {
                    return
                }

                val user = User(token)
                viewModelScope.launch {
                    userDao.insertUser(user)
                }
                _state.update { it.copy(
                    token = ""
                ) }
            }
            is UserEvent.SetToken -> {
                _state.update { it.copy(
                    token = event.token
                ) }
            }
        }
    }
}