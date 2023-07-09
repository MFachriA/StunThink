package com.projectAnya.stunthink.domain.use_case.monitoring.stunting

import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteStuntingUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        token: String,
        id: String
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val stunting = userRepository.deleteStuntingMeasurement(
                token = token,
                id = id
            )
            emit(Resource.Success(stunting.success, stunting.message, stunting.data))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}