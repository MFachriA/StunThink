package com.projectAnya.stunthink.presentation.screen.monitoring.child.edit

data class EditChildFormState(
    val name: String = "",
    val nameError: String? = null,
    val gender: String = "M",
    val date: String = "",
    val dateError: String? = null,
    val address: String = "",
    val addressError: String? = null,
)

data class EditChildSubmitState(
    val message: String = "",
    val token: String = "",
    val isLoading: Boolean = false
)
