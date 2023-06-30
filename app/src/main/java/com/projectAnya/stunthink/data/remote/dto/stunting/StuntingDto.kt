package com.projectAnya.stunthink.data.remote.dto.stunting

import com.projectAnya.stunthink.domain.model.stunting.StuntLevel
import com.projectAnya.stunthink.domain.model.stunting.Stunting

data class StuntingDto(
    val anakId: String,
    val id: String,
    val result: String,
    val timestamp: String,
    val tinggiBadan: Int,
    val umur: String
)

fun StuntingDto.toEntity(): Stunting {
    return Stunting(
        anakId = anakId,
        id = id,
        result = StuntLevel.fromName(result),
        timestamp = timestamp,
        tinggiBadan = tinggiBadan,
        umur = umur
    )
}