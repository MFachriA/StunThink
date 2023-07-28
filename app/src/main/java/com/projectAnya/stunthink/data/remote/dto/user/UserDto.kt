package com.projectAnya.stunthink.data.remote.dto.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDto(
    val jenisKelamin: String,
    val namaLengkap: String,
    val profileUrl: String,
    val tanggalLahir: String,
    val tempatLahir: String
): Parcelable