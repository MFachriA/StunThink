package com.example.stunthink.domain.repository

import com.example.stunthink.data.remote.dto.ApiResponse
import com.example.stunthink.data.remote.dto.child.ChildDto
import com.example.stunthink.data.remote.dto.login.LoginDto
import com.example.stunthink.data.remote.dto.nutrition.NutritionDto
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

    suspend fun getChildList(token: String): ApiResponse<List<ChildDto>>

    suspend fun getChildNutrition(
        token: String,
        id: String
    ): ApiResponse<List<NutritionDto>>
}