package com.pixelrabbit.testapplication.data.repository

import com.pixelrabbit.testapplication.data.models.Workout
import com.pixelrabbit.testapplication.data.models.Interval
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockWorkoutRepository @Inject constructor() {

    suspend fun getWorkout(id: Int): Result<Workout> {
        // Имитация задержки сети
        delay(1500)

        return try {
            // Создаем реалистичные mock данные для разных ID
            val workout = when (id) {
                68 -> createSampleWorkout68()
                1 -> createSampleWorkout1()
                2 -> createSampleWorkout2()
                else -> createSampleWorkout(id)
            }
            Result.success(workout)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun createSampleWorkout68(): Workout {
        return Workout(
            id = 68,
            name = "Интервальная кардио тренировка",
            intervals = listOf(
                Interval(1, 300, "work"),   // 5 минут бег
                Interval(2, 60, "rest"),    // 1 минута отдых
                Interval(3, 300, "work"),   // 5 минут бег
                Interval(4, 60, "rest"),    // 1 минута отдых
                Interval(5, 180, "work"),   // 3 минута спринт
                Interval(6, 120, "rest")    // 2 минуты отдых
            )
        )
    }

    private fun createSampleWorkout1(): Workout {
        return Workout(
            id = 1,
            name = "Утренняя разминка",
            intervals = listOf(
                Interval(1, 60, "work"),    // 1 минута
                Interval(2, 30, "rest"),    // 30 секунд
                Interval(3, 60, "work"),    // 1 минута
                Interval(4, 30, "rest"),    // 30 секунд
                Interval(5, 45, "work")     // 45 секунд
            )
        )
    }

    private fun createSampleWorkout2(): Workout {
        return Workout(
            id = 2,
            name = "Силовая тренировка",
            intervals = listOf(
                Interval(1, 180, "work"),   // 3 минуты
                Interval(2, 90, "rest"),    // 1.5 минуты
                Interval(3, 180, "work"),   // 3 минуты
                Interval(4, 90, "rest"),    // 1.5 минуты
                Interval(5, 150, "work")    // 2.5 минуты
            )
        )
    }

    private fun createSampleWorkout(id: Int): Workout {
        return Workout(
            id = id,
            name = "Тренировка #$id",
            intervals = listOf(
                Interval(1, 120, "work"),   // 2 минуты
                Interval(2, 60, "rest"),    // 1 минута
                Interval(3, 120, "work"),   // 2 минуты
                Interval(4, 60, "rest"),    // 1 минута
                Interval(5, 180, "work")    // 3 минуты
            )
        )
    }
}