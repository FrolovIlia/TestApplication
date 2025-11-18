package com.pixelrabbit.testapplication.presentation.main

import com.pixelrabbit.testapplication.data.models.Workout

data class MainUiState(
    val workoutId: String = "68",
    val isLoading: Boolean = false,
    val error: String? = null,
    val workout: Workout? = null,
    val navigateToWorkout: Boolean = false
)