package com.projectAnya.stunthink.domain.model.stunting

data class Stunting(
    val anakId: String,
    val id: String,
    val result: StuntLevel,
    val timestamp: String,
    val tinggiBadan: Int
)