package com.example.stunthink.data.remote.dto.nutrition

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NutritionDto(
    val Air: Float,
    val Ca: Float,
    val Cu: Float,
    val Energi: Float,
    val F: Float,
    val Fe2: Float,
    val Ka: Float,
    val Karbohidrat: Float,
    val Lemak: Float,
    val Na: Float,
    val Protein: Float,
    val Serat: Float,
    val VitA: Float,
    val VitB1: Float,
    val VitB2: Float,
    val VitB3: Float,
    val VitC: Float,
    val Zn2: Float,
    val anakId: String,
    val ibuId: String,
    val id: String,
    val namaMakanan: String,
    val persentaseHabis: String,
    val timastamp: String
): Parcelable