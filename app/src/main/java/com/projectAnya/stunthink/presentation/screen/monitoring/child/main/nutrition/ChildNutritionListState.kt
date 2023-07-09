package com.projectAnya.stunthink.presentation.screen.monitoring.child.main.nutrition

import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionStandardDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionStatusDto

data class ChildNutritionListState(
    val isLoading: Boolean = false,
    val nutritions: List<NutritionDto> = emptyList(),
    val nutritionStatus: NutritionStatusDto? = null,
    val nutritionStandard: NutritionStandardDto? = null,
    val error: String = ""
)
