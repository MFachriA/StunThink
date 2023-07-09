package com.projectAnya.stunthink.domain.repository

import com.projectAnya.stunthink.data.remote.dto.ApiResponse
import com.projectAnya.stunthink.data.remote.dto.child.ChildDto
import com.projectAnya.stunthink.data.remote.dto.education.EducationDto
import com.projectAnya.stunthink.data.remote.dto.login.LoginDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.FoodDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionStandardDto
import com.projectAnya.stunthink.data.remote.dto.nutrition.NutritionStatusDto
import com.projectAnya.stunthink.data.remote.dto.user.UserDto
import com.projectAnya.stunthink.domain.model.stunting.Stunting
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

    suspend fun getUserDetail(token: String): ApiResponse<UserDto>

    suspend fun getChildList(token: String): ApiResponse<List<ChildDto>>
    suspend fun getChildDetail(
        token: String,
        id: String
    ): ApiResponse<ChildDto>

    suspend fun getEducationList(token: String): ApiResponse<List<EducationDto>>

    suspend fun getChildNutrition(
        token: String,
        id: String
    ): ApiResponse<List<NutritionDto>>

    suspend fun getMotherNutrition(token: String): ApiResponse<List<NutritionDto>>

    suspend fun getNutritionStatus(
        token: String,
        startDate: String,
        endDate: String,
        isChild: Boolean?,
        childId: String?
    ): ApiResponse<NutritionStatusDto>
    suspend fun getNutritionStandard(token: String): ApiResponse<NutritionStandardDto>

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

    suspend fun getChildStunting(
        token: String,
        id: String
    ): ApiResponse<List<Stunting>>

    suspend fun addChildFood(
        token: String,
        id: String,
        foodPercentage: Float,
        foodId: String,
        foodImageUrl: String?
    ): ApiResponse<NutritionDto>

    suspend fun addMotherFood(
        token: String,
        foodPercentage: Float,
        foodId: String,
        foodImageUrl: String?
    ): ApiResponse<NutritionDto>

    suspend fun addChildStunting(
        token: String,
        id: String,
        height: Int,
        isSupine: Boolean
    ): ApiResponse<Stunting>

    suspend fun deleteChild(
        token: String,
        id: String
    ): ApiResponse<Unit>

    suspend fun deleteStuntingMeasurement(
        token: String,
        id: String
    ): ApiResponse<Unit>

    suspend fun deleteFood(
        token: String,
        id: String
    ): ApiResponse<Unit>
}
