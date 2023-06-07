package com.example.stunthink.domain.use_case.register

import com.example.stunthink.domain.common.Resource
import com.example.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmationPassword: String,
        gender: String,
        date: String,
        address: String
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val register = userRepository.register(
                name, email, password, confirmationPassword, gender, date, address
            )
            emit(Resource.Success(register.success, register.message, Unit))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}