package com.projectAnya.stunthink.presentation.screen.main.camera

import com.projectAnya.stunthink.data.remote.dto.nutrition.FoodDto

data class CameraState(
    val isLoading: Boolean = false,
    val food: FoodDto? = null,
    val error: String = ""
)
