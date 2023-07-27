package com.projectAnya.stunthink.domain.model.pregnancy

enum class PregnancyType(val displayName: String) {
    CONCEIVE("BELUM"),
    BORN("LAHIR"),
    MISCARRIAGE("KEGUGURAN"),
    PASS_AWAY("MENINGGAL"),
    OTHER("");

    companion object {
        fun fromName(name: String): PregnancyType {
            return values().find { it.displayName == name } ?: OTHER
        }
    }
}