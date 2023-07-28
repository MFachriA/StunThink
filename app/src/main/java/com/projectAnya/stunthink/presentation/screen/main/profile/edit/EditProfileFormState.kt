package com.projectAnya.stunthink.presentation.screen.main.profile.edit

data class EditProfileFormState(
    val name: String = "",
    val nameError: String? = null,
    val gender: String = "M",
    val date: String = "",
    val dateError: String? = null,
    val address: String = "",
    val addressError: String? = null,
)

data class EditProfileSubmitState(
    val message: String = "",
    val token: String = "",
    val isLoading: Boolean = false
)
