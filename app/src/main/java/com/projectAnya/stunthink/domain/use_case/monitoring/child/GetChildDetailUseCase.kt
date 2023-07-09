package com.projectAnya.stunthink.domain.use_case.monitoring.child

import com.projectAnya.stunthink.data.remote.dto.child.ChildDto
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetChildDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(token: String, id: String): Flow<Resource<ChildDto>> = flow {
        try {
            emit(Resource.Loading())
            val childDetail = userRepository.getChildDetail(token, id)
            emit(Resource.Success(childDetail.success, childDetail.message, childDetail.data))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}