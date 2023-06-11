package com.example.stunthink.data.repository

import com.example.stunthink.data.preferences.UserPreferences
import com.example.stunthink.data.remote.StunThinkApi
import com.example.stunthink.data.remote.dto.ApiResponse
import com.example.stunthink.data.remote.dto.child.ChildDto
import com.example.stunthink.data.remote.dto.login.LoginDto
import com.example.stunthink.data.remote.dto.nutrition.NutritionDto
import com.example.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: StunThinkApi,
    private val userPreferences: UserPreferences
): UserRepository {

    override suspend fun login(
        email: String, password: String
    ): ApiResponse<LoginDto> {
        return api.loginUser(email = email, password = password)
    }
    override suspend fun register(
        name: String,
        email: String,
        password: String,
        confirmationPassword: String,
        gender: String,
        date: String,
        address: String
    ): ApiResponse<Unit> {
        return api.registerUser(
            name = name,
            email = email,
            password = password,
            confirmationPassword = confirmationPassword,
            gender = gender,
            address = address,
            date = date
        )
    }

    override suspend fun saveUserToken(token: String) {
        userPreferences.saveUserToken(token)
    }

    override suspend fun deleteUserToken() {
        userPreferences.deleteUserToken()
    }

    override fun getUserToken(): Flow<String?> {
        return userPreferences.userTokenFlow
    }

    override suspend fun getChildList(token: String): ApiResponse<List<ChildDto>> {
        return api.getChildList(token)
    }

    override suspend fun getChildNutrition(
        token: String,
        id: String
    ): ApiResponse<List<NutritionDto>> {
        return api.getChildNutrition(
            auth = token,
            id = id
        )
    }


}