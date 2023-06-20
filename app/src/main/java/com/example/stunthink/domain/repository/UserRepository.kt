package com.example.stunthink.domain.repository

import com.example.stunthink.data.remote.dto.ApiResponse
import com.example.stunthink.data.remote.dto.child.ChildDto
import com.example.stunthink.data.remote.dto.education.EducationDto
import com.example.stunthink.data.remote.dto.login.LoginDto
import com.example.stunthink.data.remote.dto.nutrition.FoodDto
import com.example.stunthink.data.remote.dto.nutrition.NutritionDto
import kotlinx.coroutines.flow.Flow
import java.io.File

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
    suspend fun getEducationList(token: String): ApiResponse<List<EducationDto>>

    suspend fun getChildNutrition(
        token: String,
        id: String
    ): ApiResponse<List<NutritionDto>>

    suspend fun uploadFoodPicture(
        token: String,
        image: File
    ): ApiResponse<FoodDto>

    suspend fun registerChild(
        token: String,
        name: String,
        gender: String,
        date: String,
        address: String
    ): ApiResponse<Unit>
}