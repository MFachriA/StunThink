package com.projectAnya.stunthink.utils

import com.projectAnya.stunthink.domain.model.pregnancy.PregnancyType

object StringUtils {
    fun convertGenderEnum(gender: String): String {
        return if (gender == "M") {
            "Laki - Laki"
        } else if (gender == "F") {
            "Perempuan"
        } else {
            ""
        }
    }

    fun convertPregnancyType(type: PregnancyType): String {
        return when (type) {
            PregnancyType.CONCEIVE -> "Dalam Kandungan"
            PregnancyType.BORN -> "Sudah Lahir"
            PregnancyType.OTHER -> "-"
            else -> "-"
        }
    }
}