package com.example.stunthink.di

import android.content.Context
import androidx.room.Room
import com.example.stunthink.common.Constants
import com.example.stunthink.data.remote.StunThinkApi
import com.example.stunthink.data.repository.UserRepositoryImpl
import com.example.stunthink.database.user.UserDao
import com.example.stunthink.database.user.UserDatabase
import com.example.stunthink.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "user.db")
            .build()
    }

    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }

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
    fun provideUserRepository(api: StunThinkApi): UserRepository {
        return UserRepositoryImpl(api)
    }
}