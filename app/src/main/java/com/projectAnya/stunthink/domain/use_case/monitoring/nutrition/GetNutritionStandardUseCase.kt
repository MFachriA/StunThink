package com.projectAnya.stunthink.domain.use_case.monitoring.nutrition

import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionStandardDto
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNutritionStandardUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        token: String,
        id: String? = null
    ): Flow<Resource<NutritionStandardDto>> = flow {
        try {
            emit(Resource.Loading())
            val nutritionStatus = userRepository.getNutritionStandard(token = token, childId = id)

            emit(Resource.Success(nutritionStatus.success, nutritionStatus.message, nutritionStatus.data))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}