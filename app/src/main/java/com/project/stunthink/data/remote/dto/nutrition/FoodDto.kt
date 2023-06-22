package com.project.stunthink.data.remote.dto.nutrition

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodDto(
    val dataGizi: NutritionDto,
    val image: String?
): Parcelable