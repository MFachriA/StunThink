package com.example.stunthink.data.repository

import com.example.stunthink.data.preferences.UserPreferences
import com.example.stunthink.data.remote.StunThinkApi
import com.example.stunthink.data.remote.dto.ApiResponse
import com.example.stunthink.data.remote.dto.child.ChildDto
import com.example.stunthink.data.remote.dto.login.LoginDto
import com.example.stunthink.data.remote.dto.nutrition.FoodDto
import com.example.stunthink.data.remote.dto.nutrition.NutritionDto
import com.example.stunthink.domain.repository.UserRepository
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


//        val file = File(getFilePathFromUri(image))
//        val mediaType = getMediaTypeFromUri(image)
//        val requestFile: RequestBody = file.asRequestBody(mediaType)
//        val photoPart: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestFile)

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

//    private fun getFilePathFromUri(uri: Uri): String {
//        val context = applicationContext // Replace with your application context
//        val cursor = context.contentResolver.query(uri, null, null, null, null)
//        val filePath = if (cursor != null) {
//            cursor.moveToFirst()
//            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//            val path = cursor.getString(columnIndex)
//            cursor.close()
//            path
//        } else {
//            uri.path ?: ""
//        }
//        return filePath
//    }
//
//    private fun getMediaTypeFromUri(uri: Uri): MediaType {
//        val context = applicationContext // Replace with your application context
//        return context.contentResolver.getType(uri)?.toMediaType() ?: "image/*".toMediaType()
//    }
}