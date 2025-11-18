package com.pixelrabbit.testapplication.di

import android.content.Context
import com.pixelrabbit.testapplication.data.api.WorkoutApiService
import com.pixelrabbit.testapplication.data.repository.MockWorkoutRepository
import com.pixelrabbit.testapplication.data.repository.WorkoutRepository
import com.pixelrabbit.testapplication.utils.SoundManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSoundManager(@ApplicationContext context: Context): SoundManager {
        return SoundManager(context)
    }

    @Provides
    @Singleton
    fun provideMockWorkoutRepository(): MockWorkoutRepository {
        return MockWorkoutRepository()
    }

    @Provides
    @Singleton
    fun provideWorkoutRepository(
        mockRepository: MockWorkoutRepository
    ): WorkoutRepository {
        return WorkoutRepository(mockRepository)
    }

    // Закомментированные API зависимости остаются как есть
    /*
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideWorkoutApiService(okHttpClient: OkHttpClient): WorkoutApiService {
        return Retrofit.Builder()
            .baseUrl("https://sr111.05.testing.place/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WorkoutApiService::class.java)
    }
    */
}