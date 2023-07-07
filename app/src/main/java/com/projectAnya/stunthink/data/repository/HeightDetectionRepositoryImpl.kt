package com.projectAnya.stunthink.data.repository

import com.projectAnya.stunthink.data.remote.HeightDetectionApi
import com.projectAnya.stunthink.data.remote.dto.ApiResponse
import com.projectAnya.stunthink.data.remote.dto.height.HeightDto
import com.projectAnya.stunthink.domain.repository.HeightDetectionRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class HeightDetectionRepositoryImpl @Inject constructor(
    private val api: HeightDetectionApi
): HeightDetectionRepository {

    override suspend fun uploadHeight(image: File): ApiResponse<HeightDto> {
        return api.uploadHeight(
            image = MultipartBody.Part
                .createFormData(
                    "image",
                    image.name,
                    image.asRequestBody()
                )
        )
    }
}