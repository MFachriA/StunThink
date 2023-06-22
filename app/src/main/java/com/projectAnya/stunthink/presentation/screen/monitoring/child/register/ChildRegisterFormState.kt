package com.projectAnya.stunthink.presentation.screen.monitoring.child.register

data class ChildRegisterFormState(
    val name: String = "",
    val nameError: String? = null,
    val gender: String = "M",
    val date: String = "",
    val dateError: String? = null,
    val address: String = "",
    val addressError: String? = null,
)

data class ChildRegisterSubmitState(
    val message: String = "",
    val token: String = "",
    val isLoading: Boolean = false
)