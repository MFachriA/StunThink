package com.projectAnya.stunthink.data.remote.dto.child

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChildDto(
    val id: String,
    val jenisKelamin: String,
    val namaLengkap: String,
    val parentId: String,
    val tanggalLahir: String,
    val tempatLahir: String
): Parcelable