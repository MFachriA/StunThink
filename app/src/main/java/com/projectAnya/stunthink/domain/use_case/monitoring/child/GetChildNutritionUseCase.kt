package com.projectAnya.stunthink.domain.use_case.monitoring.child

import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionDto
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetChildNutritionUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        token: String,
        id: String,
        startDate: String,
        endDate: String
    ): Flow<Resource<List<NutritionDto>>> = flow {
        try {
            emit(Resource.Loading())
            val childList = userRepository.getChildNutrition(
                token = token,
                id = id,
                startDate,
                endDate
            )
            emit(Resource.Success(childList.success, childList.message, childList.data))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}