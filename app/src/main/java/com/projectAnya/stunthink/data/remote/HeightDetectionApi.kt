package com.projectAnya.stunthink.data.remote

import com.projectAnya.stunthink.data.remote.dto.ApiResponse
import com.projectAnya.stunthink.data.remote.dto.height.HeightDto
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface HeightDetectionApi {
    @Multipart
    @POST("predict")
    suspend fun uploadHeight(
        @Part image: MultipartBody.Part
    ): ApiResponse<HeightDto>
}