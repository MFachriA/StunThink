package com.projectAnya.stunthink.data.repository

import com.projectAnya.stunthink.data.preferences.UserPreferences
import com.projectAnya.stunthink.data.remote.StunThinkApi
import com.projectAnya.stunthink.data.remote.dto.ApiResponse
import com.projectAnya.stunthink.data.remote.dto.child.ChildDto
import com.projectAnya.stunthink.data.remote.dto.education.EducationDto
import com.projectAnya.stunthink.data.remote.dto.login.LoginDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.FoodDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionStandardDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionStatusDto
import com.projectAnya.stunthink.data.remote.dto.pregnancy.toEntity
import com.projectAnya.stunthink.data.remote.dto.stunting.toEntity
import com.projectAnya.stunthink.data.remote.dto.user.UserDto
import com.projectAnya.stunthink.domain.model.pregnancy.Pregnancy
import com.projectAnya.stunthink.domain.model.stunting.Stunting
import com.projectAnya.stunthink.domain.repository.UserRepository
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
    override suspend fun getUserDetail(token: String): ApiResponse<UserDto> {
        return api.getUserDetail(token)
    }

    override suspend fun getChildList(token: String): ApiResponse<List<ChildDto>> {
        return api.getChildList(token)
    }

    override suspend fun getChildDetail(
        token: String,
        id: String
    ): ApiResponse<ChildDto> {
        return api.getChildDetail(
            auth = token,
            id = id
        )
    }

    override suspend fun getEducationList(token: String): ApiResponse<List<EducationDto>> {
        return api.getEducationList(token)
    }

    override suspend fun getChildNutrition(
        token: String,
        id: String,
        startDate: String,
        endDate: String,
    ): ApiResponse<List<NutritionDto>> {
        return api.getChildNutrition(
            auth = token,
            id = id,
            startDate,
            endDate,
        )
    }

    override suspend fun getMotherNutrition(
        token: String,
        startDate: String,
        endDate: String
    ): ApiResponse<List<NutritionDto>> {
        return api.getMotherNutrition(
            auth = token,
            startDate,
            endDate
        )
    }

    override suspend fun getNutritionStatus(
        token: String,
        startDate: String,
        endDate: String,
        isChild: Boolean?,
        childId: String?
    ): ApiResponse<NutritionStatusDto> {
        return api.getNutritionStatus(
            auth = token,
            startDate,
            endDate,
            isChild,
            childId
        )
    }

    override suspend fun getNutritionStandard(
        token: String,
        childId: String?
    ): ApiResponse<NutritionStandardDto> {
        return api.getNutritionStandard(token, childId = childId)
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

    override suspend fun getChildStunting(
        token: String,
        id: String
    ): ApiResponse<List<Stunting>> {
        val result = api.getChildStunting(
            auth = token,
            id = id
        )
        return ApiResponse(
            result.success,
            result.message,
            result.data.map {
                it.toEntity()
            }
        )
    }

    override suspend fun getMotherPregnancy(token: String): ApiResponse<List<Pregnancy>> {
        val result = api.getMotherPregnancyList(auth = token)

        return ApiResponse(
            result.success,
            result.message,
            result.data.map {
                it.toEntity()
            }
        )
    }

    override suspend fun addChildFood(
        token: String,
        id: String,
        foodPercentage: Float,
        foodId: String,
        foodImageUrl: String?
    ): ApiResponse<NutritionDto> {
        return api.addChildFood(
            auth = token,
            id = id,
            foodPercentage = foodPercentage,
            foodId = foodId,
            foodImageUrl = foodImageUrl
        )
    }

    override suspend fun addChildStunting(
        token: String,
        id: String,
        height: Int,
        isSupine: Boolean
    ): ApiResponse<Stunting> {
        val result =  api.addChildStunting(
            auth = token,
            id = id,
            height = height,
            isSupine = isSupine
        )
        return ApiResponse(
            result.success,
            result.message,
            result.data.toEntity()
        )
    }

    override suspend fun addMotherFood(
        token: String,
        foodPercentage: Float,
        foodId: String,
        foodImageUrl: String?
    ): ApiResponse<NutritionDto> {
        return api.addMotherFood(
            auth = token,
            foodPercentage = foodPercentage,
            foodId = foodId,
            foodImageUrl = foodImageUrl
        )
    }

    override suspend fun addMotherPregnancy(
        token: String,
        pregnantDate: String,
        pregnancyType: String
    ): ApiResponse<Unit> {
        return api.addMotherPregnancy(
            auth = token,
            date = pregnantDate,
            status = pregnancyType
        )
    }

    override suspend fun deleteChild(token: String, id: String): ApiResponse<Unit> {
        return api.deleteChild(auth = token, id = id)
    }

    override suspend fun deleteStuntingMeasurement(token: String, id: String): ApiResponse<Unit> {
        return api.deleteStuntingMeasurement(auth = token, id = id)
    }

    override suspend fun deleteFood(token: String, id: String): ApiResponse<Unit> {
        return api.deleteFood(auth = token, id = id)
    }

    override suspend fun deleteMotherPregnancy(token: String, id: String): ApiResponse<Unit> {
        return api.deleteMotherPregnancy(auth = token, id = id)
    }

    override suspend fun editMotherPregnancy(
        token: String,
        id: String,
        pregnantDate: String,
        pregnancyType: String,
        birthDate: String?
    ): ApiResponse<Unit> {
        return api.updateMotherPregnancy(
            auth = token,
            id = id,
            status = pregnancyType,
            pregnantDate = pregnantDate,
            birthDate = birthDate
        )
    }
}