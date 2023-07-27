package com.projectAnya.stunthink.domain.use_case.monitoring.mother

import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddMotherPregnancyUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        token: String,
        pregnantDate: String,
        pregnancyType: String
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val pregnancy = userRepository.addMotherPregnancy(
                token = token,
                pregnantDate = pregnantDate,
                pregnancyType = pregnancyType,
            )
            emit(Resource.Success(pregnancy.success, pregnancy.message, pregnancy.data))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}