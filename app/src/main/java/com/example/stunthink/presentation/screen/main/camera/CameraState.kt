package com.example.stunthink.presentation.screen.main.camera

import com.example.stunthink.data.remote.dto.nutrition.FoodDto

data class CameraState(
    val isLoading: Boolean = false,
    val food: FoodDto? = null,
    val error: String = ""
)
