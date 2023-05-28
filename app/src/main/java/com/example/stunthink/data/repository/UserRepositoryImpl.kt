package com.example.stunthink.data.repository

import com.example.stunthink.data.remote.StunThinkApi
import com.example.stunthink.data.remote.dto.sign_in.SignInDto
import com.example.stunthink.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: StunThinkApi
): UserRepository {

    override suspend fun login(
        email: String, password: String
    ): SignInDto {
        return api.loginUser(email = email, password = password)
    }

}