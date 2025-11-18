package com.pixelrabbit.testapplication.presentation.workout

import org.osmdroid.util.GeoPoint
import com.pixelrabbit.testapplication.data.models.Workout

data class WorkoutUiState(
    val workout: Workout,
    val isRunning: Boolean = false,
    val currentIntervalIndex: Int = 0,
    val remainingTime: Int = 0,
    val gpsTrack: List<GeoPoint> = emptyList()
)