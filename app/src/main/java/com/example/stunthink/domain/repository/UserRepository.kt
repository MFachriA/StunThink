package com.example.stunthink.domain.repository

import com.example.stunthink.data.remote.dto.ApiResponse
import com.example.stunthink.data.remote.dto.login.LoginDto
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(email: String, password: String): ApiResponse<LoginDto>
    suspend fun register(
        name: String,
        email: String,
        password: String,
        confirmationPassword: String,
        gender: String,
        date: String,
        address: String
    ): ApiResponse<Unit>
    suspend fun saveUserToken(token: String)
    suspend fun deleteUserToken()
    fun getUserToken(): Flow<String?>
}