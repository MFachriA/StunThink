package com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition

import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionDto

data class ChildNutritionListState(
    val isLoading: Boolean = false,
    val nutritions: List<NutritionDto> = emptyList(),
    val error: String = ""
)
