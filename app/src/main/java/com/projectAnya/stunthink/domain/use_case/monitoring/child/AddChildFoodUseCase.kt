package com.projectAnya.stunthink.domain.use_case.monitoring.child

import android.util.Log
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionDto
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddChildFoodUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        token: String,
        id: String,
        foodPercentage: Float,
        foodId: String,
        foodImageUrl: String?
    ): Flow<Resource<NutritionDto>> = flow {
        try {
            emit(Resource.Loading())
            val food = userRepository.addChildFood(
                token = token,
                id = id,
                foodPercentage = foodPercentage,
                foodId = foodId,
                foodImageUrl = foodImageUrl
            )
            Log.d("food", food.message)
            emit(Resource.Success(food.success, food.message, food.data))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}