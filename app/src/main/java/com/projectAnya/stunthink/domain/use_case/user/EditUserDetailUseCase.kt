package com.projectAnya.stunthink.domain.use_case.user

import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EditUserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        token: String,
        name: String,
        gender: String,
        date: String,
        address: String
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val register = userRepository.editProfile(
                token , name, gender, date, address
            )
            emit(Resource.Success(register.success, register.message, Unit))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}