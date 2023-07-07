package com.projectAnya.stunthink.domain.di

import android.app.Application
import android.content.Context
import com.projectAnya.stunthink.data.preferences.UserPreferences
import com.projectAnya.stunthink.data.remote.HeightDetectionApi
import com.projectAnya.stunthink.data.remote.StunThinkApi
import com.projectAnya.stunthink.data.repository.HeightDetectionRepositoryImpl
import com.projectAnya.stunthink.data.repository.UserRepositoryImpl
import com.projectAnya.stunthink.domain.common.Constants
import com.projectAnya.stunthink.domain.repository.HeightDetectionRepository
import com.projectAnya.stunthink.domain.repository.UserRepository
import com.projectAnya.stunthink.domain.use_case.child_register.ChildRegisterUseCase
import com.projectAnya.stunthink.domain.use_case.education.GetEducationListUseCase
import com.projectAnya.stunthink.domain.use_case.login.LoginUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.child.AddChildStuntingUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.child.GetChildListUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.child.GetChildNutritionUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.child.GetChildStuntingUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.food_detection.UploadFoodPictureUseCase
import com.projectAnya.stunthink.domain.use_case.monitoring.height_detection.UploadHeightPictureUseCase
import com.projectAnya.stunthink.domain.use_case.register.RegisterUseCase
import com.projectAnya.stunthink.domain.use_case.user.GetUserTokenUseCase
import com.projectAnya.stunthink.domain.use_case.user.SaveUserTokenUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateAddressUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateConfirmationPasswordUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateDateUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateDropDownUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateEmailUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateHeightUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidateNameUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidatePasswordUseCase
import com.projectAnya.stunthink.domain.use_case.validate.ValidatePlaceOfBirthUseCase
import com.projectAnya.stunthink.utils.PhotoUriManager
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

    @Provides
    @Singleton
    fun provideHeightDetectionApi(): HeightDetectionApi {
        return Retrofit.Builder()
            .baseUrl(Constants.HEIGHT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeightDetectionApi::class.java)
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

    @Provides
    @Singleton
    fun provideHeightDetectionRepository(api: HeightDetectionApi): HeightDetectionRepository {
        return HeightDetectionRepositoryImpl(api)
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
    fun provideValidateHeightUseCase(): ValidateHeightUseCase {
        return ValidateHeightUseCase()
    }

    @Provides
    fun provideValidateDropDownUseCase(): ValidateDropDownUseCase {
        return ValidateDropDownUseCase()
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

    @Provides
    fun provideGetChildStuntingUseCase(userRepository: UserRepository): GetChildStuntingUseCase {
        return GetChildStuntingUseCase(userRepository)
    }

    @Provides
    fun provideAddChildStuntingUseCase(userRepository: UserRepository): AddChildStuntingUseCase {
        return AddChildStuntingUseCase(userRepository)
    }

    @Provides
    fun provideUploadHeightPictureUseCase(heightDetectionRepository: HeightDetectionRepository): UploadHeightPictureUseCase {
        return UploadHeightPictureUseCase(heightDetectionRepository)
    }

}