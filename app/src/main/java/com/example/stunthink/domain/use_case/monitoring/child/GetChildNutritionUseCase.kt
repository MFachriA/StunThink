package com.example.stunthink.domain.use_case.monitoring.child

import com.example.stunthink.data.remote.dto.nutrition.NutritionDto
import com.example.stunthink.domain.common.Resource
import com.example.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetChildNutritionUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(token: String, id: String): Flow<Resource<List<NutritionDto>>> = flow {
        try {
            emit(Resource.Loading())
            val childList = userRepository.getChildNutrition(token = token, id = id)
            emit(Resource.Success(childList.success, childList.message, childList.data))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}