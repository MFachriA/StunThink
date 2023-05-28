package com.example.stunthink.data.remote

import com.example.stunthink.data.remote.dto.sign_in.SignInDto
import com.example.stunthink.data.remote.dto.sign_up.SignUpDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface StunThinkApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): SignInDto

    @FormUrlEncoded
    @POST("user/register")
    suspend fun registerUser(
        @Field("namaLengkap") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") passwordConfirmation: String,
        @Field("tempatLahir") address: String,
        @Field("tanggalLahir") date: String,
        @Field("jenisKelamin") gender: String
    ): SignUpDto
}