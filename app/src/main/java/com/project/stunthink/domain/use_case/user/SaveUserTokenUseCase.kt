package com.project.stunthink.domain.use_case.user

import com.project.stunthink.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(token: String) {
        userRepository.saveUserToken(token)
    }
}