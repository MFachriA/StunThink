package com.example.stunthink.data.remote

import com.example.stunthink.data.remote.dto.ApiResponse
import com.example.stunthink.data.remote.dto.child.ChildDto
import com.example.stunthink.data.remote.dto.login.LoginDto
import com.example.stunthink.data.remote.dto.nutrition.NutritionDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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

//    @FormUrlEncoded
//    @POST("anak")
//    fun registerChild(
//        @Header("auth")auth:String,
//        @Field("namaLengkap") name: String,
//        @Field("tempatLahir") address: String,
//        @Field("tanggalLahir") date: String,
//        @Field("jenisKelamin") gender: String
//    ): Call<ResponseRegister>

    @GET("anak")
    suspend fun getChildList(
        @Header("auth") auth: String
    ): ApiResponse<List<ChildDto>>

    @GET("gizi/anak/{id}")
    suspend fun getChildNutrition(
        @Header("auth") auth: String,
        @Path("id") id: String
    ): ApiResponse<List<NutritionDto>>

//    @GET("anak/{id}")
//    fun child(
//        @Header("auth") auth:String,
//        @Path("id") id: Int
//    ): Call<List<ResponseChildListItem>>
//
//    @GET("education")
//    fun education(
//        @Header("auth") auth:String
//    ): Call<List<ResponseEducationItem>>

}