package com.projectAnya.stunthink.domain.repository

import com.projectAnya.stunthink.data.remote.dto.ApiResponse
import com.projectAnya.stunthink.data.remote.dto.height.HeightDto
import java.io.File

interface HeightDetectionRepository {

    suspend fun uploadHeight(image: File): ApiResponse<HeightDto>
}