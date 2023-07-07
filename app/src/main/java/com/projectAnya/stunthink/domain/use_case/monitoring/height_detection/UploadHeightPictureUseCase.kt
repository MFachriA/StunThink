package com.projectAnya.stunthink.domain.use_case.monitoring.height_detection

import com.projectAnya.stunthink.data.remote.dto.height.HeightDto
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.repository.HeightDetectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class UploadHeightPictureUseCase @Inject constructor(
    private val repository: HeightDetectionRepository
) {
    operator fun invoke(
        image: File
    ): Flow<Resource<HeightDto>> = flow {
        try {
            emit(Resource.Loading())
            val height = repository.uploadHeight(
                image = image
            )
            emit(Resource.Success(height.success, height.message, height.data))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}