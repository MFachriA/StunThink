package com.projectAnya.stunthink.domain.model.stunting

enum class StuntLevel(val displayName: String) {
    SEVERELY_STUNTED("Severely Stunted"),
    STUNTED("Stunted"),
    NORMAL("Normal"),
    TALL("Tinggi"),
    OTHER("");

    companion object {
        fun fromName(name: String): StuntLevel {
            return values().find { it.displayName == name } ?: OTHER
        }
    }
}