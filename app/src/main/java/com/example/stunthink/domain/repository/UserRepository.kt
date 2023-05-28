package com.example.stunthink.domain.repository

import com.example.stunthink.data.remote.dto.sign_in.SignInDto

interface UserRepository {

    suspend fun login(email: String, password: String): SignInDto
}