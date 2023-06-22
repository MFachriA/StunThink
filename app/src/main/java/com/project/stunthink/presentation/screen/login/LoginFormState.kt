package com.project.stunthink.presentation.screen.login

data class LoginFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)

data class LoginSubmitState(
    val message: String = "",
    val token: String = "",
    val isLoading: Boolean = false
)
