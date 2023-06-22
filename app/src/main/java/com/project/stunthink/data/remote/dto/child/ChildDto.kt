package com.project.stunthink.data.remote.dto.child

data class ChildDto(
    val id: String,
    val jenisKelamin: String,
    val namaLengkap: String,
    val parentId: String,
    val tanggalLahir: String,
    val tempatLahir: String
)