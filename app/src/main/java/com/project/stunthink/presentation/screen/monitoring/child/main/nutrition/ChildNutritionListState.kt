package com.project.stunthink.presentation.screen.monitoring.child.main.nutrition

import com.project.stunthink.data.remote.dto.nutrition.NutritionDto

data class ChildNutritionListState(
    val isLoading: Boolean = false,
    val nutritions: List<NutritionDto> = emptyList(),
    val error: String = ""
)
