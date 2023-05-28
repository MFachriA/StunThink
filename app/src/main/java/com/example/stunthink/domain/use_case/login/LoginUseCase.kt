package com.example.stunthink.domain.use_case.login

import com.example.stunthink.common.Resource
import com.example.stunthink.data.remote.dto.sign_in.SignInDto
import com.example.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<SignInDto>> = flow {
        try {
            emit(Resource.Loading())
            val login = userRepository.login(email, password)
            emit(Resource.Success(login))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}