package com.projectAnya.stunthink.data.remote.dto.height

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HeightDto(
    val image_url: String,
    val tinggiBadan: Double
): Parcelable
