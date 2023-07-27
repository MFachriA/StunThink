package com.projectAnya.stunthink.presentation.screen.monitoring.mother.main.pregnancy.add

data class AddPregnancyFormState(
    val date: String = "",
    val dateError: String? = null
)

data class AddPregnancySubmitState(
    val message: String = "",
    val token: String = "",
    val isLoading: Boolean = false
)