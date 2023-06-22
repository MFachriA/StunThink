package com.projectAnya.stunthink.domain.use_case.user

import com.projectAnya.stunthink.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute() {
        userRepository.deleteUserToken()
    }
}