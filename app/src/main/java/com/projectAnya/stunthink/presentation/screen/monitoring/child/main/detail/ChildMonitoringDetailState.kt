package com.projectAnya.stunthink.presentation.screen.monitoring.child.main.detail

import com.projectAnya.stunthink.data.remote.dto.child.ChildDto

data class ChildMonitoringDetailState(
    val message: String = "",
    val childDetail: ChildDto? = null,
    val isLoading: Boolean = false
)

data class ChildMonitoringDeleteState(
    val message: String = "",
    val isLoading: Boolean = false
)