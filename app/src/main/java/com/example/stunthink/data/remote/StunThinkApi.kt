package com.example.stunthink.data.remote

import com.example.stunthink.data.remote.dto.ApiResponse
import com.example.stunthink.data.remote.dto.login.LoginDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}