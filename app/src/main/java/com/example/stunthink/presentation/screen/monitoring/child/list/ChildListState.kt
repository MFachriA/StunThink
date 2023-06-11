package com.example.stunthink.presentation.screen.monitoring.child.list

import com.example.stunthink.data.remote.dto.child.ChildDto

data class ChildListState(
    val isLoading: Boolean = false,
    val childs: List<ChildDto> = emptyList(),
    val error: String = ""
)
