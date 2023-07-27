package com.projectAnya.stunthink.domain.model.pregnancy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pregnancy(
    val id: String,
    val pregnancyType: PregnancyType,
    val pregnantDate: String,
    val birthDate: String?,
    val userDetailId: String
): Parcelable