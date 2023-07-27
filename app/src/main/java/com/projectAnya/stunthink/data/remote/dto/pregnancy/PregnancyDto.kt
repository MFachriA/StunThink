package com.projectAnya.stunthink.data.remote.dto.pregnancy

import com.projectAnya.stunthink.domain.model.pregnancy.Pregnancy
import com.projectAnya.stunthink.domain.model.pregnancy.PregnancyType

data class PregnancyDto(
    val id: String,
    val lahir: String,
    val tanggalHamil: String,
    val tanggalKelahiran: String?,
    val userDetailId: String
)

fun PregnancyDto.toEntity(): Pregnancy {
    return Pregnancy(
        id = id,
        pregnancyType = PregnancyType.fromName(lahir),
        pregnantDate = tanggalHamil,
        birthDate = tanggalKelahiran,
        userDetailId = userDetailId
    )
}