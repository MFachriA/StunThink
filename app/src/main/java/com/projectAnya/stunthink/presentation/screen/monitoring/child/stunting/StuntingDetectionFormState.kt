package com.projectAnya.stunthink.presentation.screen.monitoring.child.stunting

data class ChildRegisterFormState(
    val height: String = "",
    val heightError: String? = null,
    val supine: String = "",
    val supineError: String? = null,
)

data class ChildRegisterSubmitState(
    val message: String = "",
    val token: String = "",
    val isLoading: Boolean = false
)