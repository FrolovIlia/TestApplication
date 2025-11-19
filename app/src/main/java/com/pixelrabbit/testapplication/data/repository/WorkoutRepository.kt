package com.pixelrabbit.testapplication.data.repository

import com.pixelrabbit.testapplication.data.api.WorkoutApiService
import com.pixelrabbit.testapplication.data.models.Interval
import com.pixelrabbit.testapplication.data.models.Workout

class WorkoutRepository(
    private val api: WorkoutApiService
) {

    suspend fun loadWorkout(id: Int): Workout {
        val response = api.getWorkout(id)
        val timer = response.timer

        return Workout(
            id = timer.timerId,
            name = timer.title, // вместо title
            intervals = timer.intervals.mapIndexed { index, dto ->
                Interval(
                    id = index + 1,
                    duration = dto.time,
                    type = if (dto.title.lowercase().contains("отдых")) "rest" else "work"
                )
            }
        )
    }
}
