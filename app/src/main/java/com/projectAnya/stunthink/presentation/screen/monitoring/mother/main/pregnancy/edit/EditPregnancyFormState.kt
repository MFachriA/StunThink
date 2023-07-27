package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy.edit

data class EditPregnancyFormState(
    val date: String = "",
    val dateError: String? = null
)

data class EditPregnancySubmitState(
    val message: String = "",
    val token: String = "",
    val isLoading: Boolean = false
)