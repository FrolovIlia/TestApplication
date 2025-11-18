package com.pixelrabbit.testapplication.data.repository

import com.pixelrabbit.testapplication.data.models.Workout
import javax.inject.Inject

class WorkoutRepository @Inject constructor(
    private val mockRepository: MockWorkoutRepository
) {
    suspend fun getWorkout(id: Int): Result<Workout> {
        // Используем mock данные вместо реального API
        return mockRepository.getWorkout(id)

        // Позже можно будет переключиться на реальный API:
        /*
        return try {
            val response = apiService.getWorkout(id)
            // преобразование response в Workout
        } catch (e: Exception) {
            // fallback на mock данные
            mockRepository.getWorkout(id)
        }
        */
    }
}