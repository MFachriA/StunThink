package com.projectAnya.stunthink.domain.use_case.user

import com.projectAnya.stunthink.data.remote.dto.user.UserDto
import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(token: String): Flow<Resource<UserDto>> = flow {
        try {
            emit(Resource.Loading())
            val userDetail = userRepository.getUserDetail(token)
            emit(Resource.Success(userDetail.success, userDetail.message, userDetail.data))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}