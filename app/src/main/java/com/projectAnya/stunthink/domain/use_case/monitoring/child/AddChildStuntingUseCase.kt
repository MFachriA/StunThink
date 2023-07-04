package com.projectAnya.stunthink.domain.use_case.monitoring.child

import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.model.stunting.Stunting
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddChildStuntingUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        token: String,
        id: String,
        height: Int,
        isSupine: Boolean
    ): Flow<Resource<Stunting>> = flow {
        try {
            emit(Resource.Loading())
            val stunting = userRepository.addChildStunting(
                token = token,
                id = id,
                height = height,
                isSupine = isSupine
            )
            emit(Resource.Success(stunting.success, stunting.message, stunting.data))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}