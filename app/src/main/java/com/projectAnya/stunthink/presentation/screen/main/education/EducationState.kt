package com.projectAnya.stunthink.presentation.screen.main.education

import com.projectAnya.stunthink.data.remote.dto.education.EducationDto

data class EducationState(
    val isLoading: Boolean = false,
    val educationList: List<EducationDto> = emptyList(),
    val error: String = ""
)
