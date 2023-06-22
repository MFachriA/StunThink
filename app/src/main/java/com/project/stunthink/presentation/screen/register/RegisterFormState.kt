package com.project.stunthink.presentation.screen.register

data class RegisterFormState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmationPassword: String = "",
    val confirmationPasswordError: String? = null,
    val gender: String = "M",
    val date: String = "",
    val dateError: String? = null,
    val address: String = "",
    val addressError: String? = null,
)

data class RegisterSubmitState(
    val message: String = "",
    val token: String = "",
    val isLoading: Boolean = false
)
