package com.projectAnya.stunthink.domain.use_case.monitoring.food

import com.projectAnya.stunthink.data.remote.dto.nutrition.FoodDto
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class UploadFoodPictureUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        token: String,
        image: File
    ): Flow<Resource<FoodDto>> = flow {
        try {
            emit(Resource.Loading())
            val food = userRepository.uploadFoodPicture(
                token = token,
                image = image
            )
            emit(Resource.Success(food.success, food.message, food.data))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}