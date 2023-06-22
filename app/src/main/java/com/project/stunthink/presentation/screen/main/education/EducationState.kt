package com.project.stunthink.presentation.screen.main.education

import com.project.stunthink.data.remote.dto.education.EducationDto

data class EducationState(
    val isLoading: Boolean = false,
    val educationList: List<EducationDto> = emptyList(),
    val error: String = ""
)
