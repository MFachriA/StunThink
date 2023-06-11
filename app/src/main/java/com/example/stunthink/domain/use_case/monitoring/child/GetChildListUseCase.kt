package com.example.stunthink.domain.use_case.monitoring.child

import com.example.stunthink.data.remote.dto.child.ChildDto
import com.example.stunthink.domain.common.Resource
import com.example.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetChildListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(token: String): Flow<Resource<List<ChildDto>>> = flow {
        try {
            emit(Resource.Loading())
            val childList = userRepository.getChildList(token)
            emit(Resource.Success(childList.success, childList.message, childList.data))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}