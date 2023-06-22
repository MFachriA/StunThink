package com.project.stunthink.domain.use_case.user

import com.project.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun execute(): Flow<String?> {
        return userRepository.getUserToken()
    }
}