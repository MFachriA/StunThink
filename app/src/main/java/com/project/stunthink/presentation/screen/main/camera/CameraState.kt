package com.project.stunthink.presentation.screen.main.camera

import com.project.stunthink.data.remote.dto.nutrition.FoodDto

data class CameraState(
    val isLoading: Boolean = false,
    val food: FoodDto? = null,
    val error: String = ""
)
