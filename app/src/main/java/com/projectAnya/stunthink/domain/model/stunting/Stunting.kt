package com.projectAnya.stunthink.domain.model.stunting

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stunting(
    val anakId: String,
    val id: String,
    val result: StuntLevel,
    val timestamp: String,
    val tinggiBadan: Float,
    val umur: String?
): Parcelable