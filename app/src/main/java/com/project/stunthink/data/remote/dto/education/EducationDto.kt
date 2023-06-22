package com.project.stunthink.data.remote.dto.education

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EducationDto(
    val author: String,
    val content: String,
    val desc: String,
    val id: String,
    val publishedAt: String,
    val title: String,
    val urlToImage: String
): Parcelable