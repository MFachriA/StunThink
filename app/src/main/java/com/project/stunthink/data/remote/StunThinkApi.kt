package com.project.stunthink.data.remote

import com.project.stunthink.data.remote.dto.ApiResponse
import com.project.stunthink.data.remote.dto.child.ChildDto
import com.project.stunthink.data.remote.dto.education.EducationDto
import com.project.stunthink.data.remote.dto.login.LoginDto
import com.project.stunthink.data.remote.dto.nutrition.FoodDto
import com.project.stunthink.data.remote.dto.nutrition.NutritionDto
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface StunThinkApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): ApiResponse<LoginDto>

    @FormUrlEncoded
    @POST("user/register")
    suspend fun registerUser(
        @Field("namaLengkap") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmationPassword: String,
        @Field("tempatLahir") address: String,
        @Field("tanggalLahir") date: String,
        @Field("jenisKelamin") gender: String
    ): ApiResponse<Unit>

    @FormUrlEncoded
    @POST("anak")
    suspend fun registerChild(
        @Header("auth")auth:String,
        @Field("namaLengkap") name: String,
        @Field("tempatLahir") address: String,
        @Field("tanggalLahir") date: String,
        @Field("jenisKelamin") gender: String
    ): ApiResponse<Unit>

    @GET("anak")
    suspend fun getChildList(
        @Header("auth") auth: String
    ): ApiResponse<List<ChildDto>>

    @GET("history/gizi/anak/{id}")
    suspend fun getChildNutrition(
        @Header("auth") auth: String,
        @Path("id") id: String
    ): ApiResponse<List<NutritionDto>>

    @Multipart
    @POST("history/detect")
    suspend fun uploadPhoto(
        @Header("auth") auth: String,
        @Part image: MultipartBody.Part
    ): ApiResponse<FoodDto>

    @GET("education")
    suspend fun getEducationList(
        @Header("auth") auth: String
    ): ApiResponse<List<EducationDto>>


}