package com.projectAnya.stunthink.presentation.screen.monitoring.child.list

import com.projectAnya.stunthink.data.remote.dto.child.ChildDto

data class ChildListState(
    val isLoading: Boolean = false,
    val childList: List<ChildDto> = emptyList(),
    val error: String = ""
)
