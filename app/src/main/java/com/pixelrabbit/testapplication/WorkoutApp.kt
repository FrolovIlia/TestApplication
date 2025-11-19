package com.pixelrabbit.testapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pixelrabbit.testapplication.data.models.Workout
import com.pixelrabbit.testapplication.presentation.main.MainScreen
import com.pixelrabbit.testapplication.presentation.workout.WorkoutScreen

@Composable
fun WorkoutApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(
                onNavigateToWorkout = { workout ->
                    WorkoutHolder.workout = workout
                    navController.navigate("workout")
                }
            )
        }
        composable("workout") {
            WorkoutScreen(
                onBack = {
                    WorkoutHolder.workout = null
                    navController.popBackStack()
                }
            )
        }
    }
}

// Временное хранилище для передачи данных между экранами
object WorkoutHolder {
    var workout: Workout? = null
}