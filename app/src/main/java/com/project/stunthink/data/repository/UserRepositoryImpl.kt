package com.project.stunthink.data.repository

import com.project.stunthink.data.preferences.UserPreferences
import com.project.stunthink.data.remote.StunThinkApi
import com.project.stunthink.data.remote.dto.ApiResponse
import com.project.stunthink.data.remote.dto.child.ChildDto
import com.project.stunthink.data.remote.dto.education.EducationDto
import com.project.stunthink.data.remote.dto.login.LoginDto
import com.project.stunthink.data.remote.dto.nutrition.FoodDto
import com.project.stunthink.data.remote.dto.nutrition.NutritionDto
import com.project.stunthink.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
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

    override suspend fun getEducationList(token: String): ApiResponse<List<EducationDto>> {
        return api.getEducationList(token)
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

    override suspend fun uploadFoodPicture(
        token: String,
        image: File
    ): ApiResponse<FoodDto> {

        return api.uploadPhoto(
            auth = token,
            image = MultipartBody.Part
                .createFormData(
                    "image",
                    image.name,
                    image.asRequestBody()
                )
        )
    }

    override suspend fun registerChild(
        token: String,
        name: String,
        gender: String,
        date: String,
        address: String
    ): ApiResponse<Unit> {
        return api.registerChild(
            auth = token,
            name = name,
            gender = gender,
            address = address,
            date = date
        )
    }
}