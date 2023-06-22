package com.project.stunthink.utils

object StringUtils {
    fun convertGenderEnum(gender: String): String {
        return if (gender == "M") {
            "Laki-Laki"
        } else if (gender == "F") {
            "Perempuan"
        } else {
            ""
        }
    }
}