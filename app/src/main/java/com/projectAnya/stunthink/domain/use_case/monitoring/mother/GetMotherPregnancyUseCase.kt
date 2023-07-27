package com.projectAnya.stunthink.domain.use_case.monitoring.mother

import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.model.pregnancy.Pregnancy
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetMotherPregnancyUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(token: String, ): Flow<Resource<List<Pregnancy>>> = flow {
        try {
            emit(Resource.Loading())
            val pregnancyList = userRepository.getMotherPregnancy(token = token)
            emit(Resource.Success(pregnancyList.success, pregnancyList.message, pregnancyList.data))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}