package com.example.stunthink.domain.di

import android.app.Application
import android.content.Context
import com.example.stunthink.domain.common.Constants
import com.example.stunthink.data.preferences.UserPreferences
import com.example.stunthink.data.remote.StunThinkApi
import com.example.stunthink.data.repository.UserRepositoryImpl
import com.example.stunthink.domain.repository.UserRepository
import com.example.stunthink.domain.use_case.child_register.ChildRegisterUseCase
import com.example.stunthink.domain.use_case.education.GetEducationListUseCase
import com.example.stunthink.domain.use_case.login.LoginUseCase
import com.example.stunthink.domain.use_case.monitoring.child.GetChildListUseCase
import com.example.stunthink.domain.use_case.monitoring.child.GetChildNutritionUseCase
import com.example.stunthink.domain.use_case.monitoring.food_detection.UploadFoodPictureUseCase
import com.example.stunthink.domain.use_case.register.RegisterUseCase
import com.example.stunthink.domain.use_case.user.GetUserTokenUseCase
import com.example.stunthink.domain.use_case.user.SaveUserTokenUseCase
import com.example.stunthink.domain.use_case.validate.ValidateAddressUseCase
import com.example.stunthink.domain.use_case.validate.ValidateConfirmationPasswordUseCase
import com.example.stunthink.domain.use_case.validate.ValidateDateUseCase
import com.example.stunthink.domain.use_case.validate.ValidateEmailUseCase
import com.example.stunthink.domain.use_case.validate.ValidateNameUseCase
import com.example.stunthink.domain.use_case.validate.ValidatePasswordUseCase
import com.example.stunthink.domain.use_case.validate.ValidatePlaceOfBirthUseCase
import com.example.stunthink.utils.PhotoUriManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Context
    @Provides
    fun provideContext(application: Application): Context{
        return application.applicationContext
    }

    // API
    @Provides
    @Singleton
    fun provideStunThinkApi(): StunThinkApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StunThinkApi::class.java)
    }

    // PREFERENCES
    @Provides
    @Singleton
    fun provideUserPreferences(context: Context): UserPreferences {
        return UserPreferences(context)
    }

    // REPOSITORY
    @Provides
    @Singleton
    fun provideUserRepository(api: StunThinkApi, userPreferences: UserPreferences): UserRepository {
        return UserRepositoryImpl(api, userPreferences)
    }

    // CAMERA
    @Provides
    @Singleton
    fun providePhotoManager(context: Context): PhotoUriManager {
        return PhotoUriManager(context)
    }

    // USE CASE
    @Provides
    fun provideValidateNameUseCase(): ValidateNameUseCase {
        return ValidateNameUseCase()
    }
    @Provides
    fun provideValidateEmailUseCase(): ValidateEmailUseCase {
        return ValidateEmailUseCase()
    }

    @Provides
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }

    @Provides
    fun provideValidateConfirmationPasswordUseCase(): ValidateConfirmationPasswordUseCase {
        return ValidateConfirmationPasswordUseCase()
    }

    @Provides
    fun provideValidateDateUseCase(): ValidateDateUseCase {
        return ValidateDateUseCase()
    }

    @Provides
    fun provideValidateAddressUseCase(): ValidateAddressUseCase {
        return ValidateAddressUseCase()
    }

    @Provides
    fun provideValidatePlaceOfBirthUseCase(): ValidatePlaceOfBirthUseCase {
        return ValidatePlaceOfBirthUseCase()
    }

    @Provides
    fun provideGetUserTokenUseCase(userRepository: UserRepository): GetUserTokenUseCase {
        return GetUserTokenUseCase(userRepository)
    }

    @Provides
    fun provideSaveUserTokenUseCase(userRepository: UserRepository): SaveUserTokenUseCase {
        return SaveUserTokenUseCase(userRepository)
    }

    @Provides
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase {
        return LoginUseCase(userRepository)
    }

    @Provides
    fun provideRegisterUseCase(userRepository: UserRepository): RegisterUseCase {
        return RegisterUseCase(userRepository)
    }

    @Provides
    fun provideGetChildListUseCase(userRepository: UserRepository): GetChildListUseCase {
        return GetChildListUseCase(userRepository)
    }

    @Provides
    fun provideGetChildNutritionUseCase(userRepository: UserRepository): GetChildNutritionUseCase {
        return GetChildNutritionUseCase(userRepository)
    }

    @Provides
    fun provideUploadFoodPictureUseCase(userRepository: UserRepository): UploadFoodPictureUseCase {
        return UploadFoodPictureUseCase(userRepository)
    }

    @Provides
    fun provideGetEducationListUseCase(userRepository: UserRepository): GetEducationListUseCase {
        return GetEducationListUseCase(userRepository)
    }

    @Provides
    fun provideChildRegisterUseCase(userRepository: UserRepository): ChildRegisterUseCase {
        return ChildRegisterUseCase(userRepository)
    }
}