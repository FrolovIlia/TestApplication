package com.pixelrabbit.testapplication.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface WorkoutApiService {
    @GET("interval-timers/{id}")
    suspend fun getWorkout(
        @Path("id") id: Int,
        @Header("App-Token") token: String = "secret"
    ): WorkoutResponse
}