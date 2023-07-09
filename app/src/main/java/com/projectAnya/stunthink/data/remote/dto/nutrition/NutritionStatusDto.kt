package com.projectAnya.stunthink.data.remote.dto.nutrition

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NutritionStatusDto(
    val _sum: NutritionDetailDto
):Parcelable