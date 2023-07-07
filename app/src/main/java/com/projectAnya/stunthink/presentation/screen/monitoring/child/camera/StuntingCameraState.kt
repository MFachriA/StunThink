package com.projectAnya.stunthink.presentation.screen.monitoring.child.camera

import com.projectAnya.stunthink.data.remote.dto.height.HeightDto

data class StuntingCameraState(
    val isLoading: Boolean = false,
    val food: HeightDto? = null,
    val error: String = ""
)
