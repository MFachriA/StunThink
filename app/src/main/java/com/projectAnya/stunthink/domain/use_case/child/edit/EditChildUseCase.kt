package com.projectAnya.stunthink.domain.use_case.child.edit

import com.projectAnya.stunthink.domain.common.Resource
import com.projectAnya.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class EditChildUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        token: String,
        id: String,
        name: String,
        gender: String,
        date: String,
        address: String
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val child = userRepository.editChild(
                token, id, name, gender, date, address
            )
            emit(Resource.Success(child.success, child.message, Unit))
        } catch(e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Couldn't reach server. Check your internet connection"))
        }
    }
}