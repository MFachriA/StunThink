package com.projectAnya.stunthink.presentation.screen.monitoring.child.stuntingdetection

data class ChildRegisterFormState(
    val height: Int = 0,
    val heightError: String? = null,
    val supine: Boolean = false,
    val supineError: String? = null,
)

data class ChildRegisterSubmitState(
    val message: String = "",
    val token: String = "",
    val isLoading: Boolean = false
)