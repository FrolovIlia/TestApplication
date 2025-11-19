package com.pixelrabbit.testapplication.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface WorkoutApiService {

    @GET("api/v2/interval-timers/{id}")
    suspend fun getWorkout(
        @Path("id") id: Int,
        @Header("App-Token") token: String = "secret",
        @Header("Authorization") auth: String = "Bearer pdhO16atBIXogpPzaLDjDcl5Gpmbz9Mdl1mjhrhWZBuOgNCgxDlk7mMIbFcEc7mj"
    ): WorkoutResponse
}
