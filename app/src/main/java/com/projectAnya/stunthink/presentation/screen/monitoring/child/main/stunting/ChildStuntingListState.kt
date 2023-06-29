package com.projectAnya.stunthink.presentation.screen.monitoring.child.main.stunting

import com.projectAnya.stunthink.domain.model.stunting.Stunting

data class ChildStuntingListState(
    val isLoading: Boolean = false,
    val stuntings: List<Stunting> = emptyList(),
    val error: String = ""
)
