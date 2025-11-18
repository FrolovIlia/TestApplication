package com.pixelrabbit.testapplication.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WorkoutResponse(
    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "intervals")
    val intervals: List<IntervalResponse>
)

@JsonClass(generateAdapter = true)
data class IntervalResponse(
    @Json(name = "id")
    val id: Int,

    @Json(name = "duration")
    val duration: Int,

    @Json(name = "type")
    val type: String
)