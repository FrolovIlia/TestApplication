package com.pixelrabbit.testapplication.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WorkoutResponse(
    @Json(name = "timer")
    val timer: TimerDto
)

@JsonClass(generateAdapter = true)
data class TimerDto(
    @Json(name = "timer_id")
    val timerId: Int,

    val title: String,

    @Json(name = "total_time")
    val totalTime: Int,

    val intervals: List<IntervalDto>
)

@JsonClass(generateAdapter = true)
data class IntervalDto(
    val title: String,
    @Json(name = "time")
    val time: Int
)
