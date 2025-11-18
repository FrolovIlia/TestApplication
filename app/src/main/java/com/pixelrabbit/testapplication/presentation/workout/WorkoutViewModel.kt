package com.pixelrabbit.testapplication.presentation.workout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import com.pixelrabbit.testapplication.WorkoutHolder
import com.pixelrabbit.testapplication.data.models.Workout
import com.pixelrabbit.testapplication.utils.SoundManager
import kotlin.random.Random

class WorkoutViewModel(private val soundManager: SoundManager) {
    private val workout: Workout = checkNotNull(WorkoutHolder.workout)
    private var timerJob: Job? = null
    private var gpsJob: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val initialLatitude = 55.755826
    private val initialLongitude = 37.617300
    private var currentLatitude = initialLatitude
    private var currentLongitude = initialLongitude

    var uiState by mutableStateOf(
        WorkoutUiState(
            workout = workout,
            remainingTime = workout.intervals.firstOrNull()?.duration ?: 0
        )
    )
        private set

    fun onStartStopClick() {
        if (uiState.isRunning) {
            stopWorkout()
        } else {
            startWorkout()
        }
    }

    private fun startWorkout() {
        currentLatitude = initialLatitude
        currentLongitude = initialLongitude

        uiState = uiState.copy(
            isRunning = true,
            currentIntervalIndex = 0,
            gpsTrack = listOf(GeoPoint(currentLatitude, currentLongitude)),
            remainingTime = workout.intervals.first().duration
        )

        // Один пик при начале тренировки
        coroutineScope.launch {
            soundManager.playBeep(true)
        }

        startTimer()
        startGpsTracking()
    }

    private fun stopWorkout() {
        uiState = uiState.copy(isRunning = false)
        timerJob?.cancel()
        gpsJob?.cancel()
        timerJob = null
        gpsJob = null

        // Два пика при остановке тренировки
        coroutineScope.launch {
            soundManager.playBeep(false)
        }
    }

    private fun startTimer() {
        timerJob = coroutineScope.launch {
            var isFirstInterval = true

            while (uiState.isRunning && uiState.currentIntervalIndex < workout.intervals.size) {
                val currentInterval = workout.intervals[uiState.currentIntervalIndex]

                // Один пик при начале каждого интервала (кроме первого)
                if (!isFirstInterval) {
                    soundManager.playBeep(true)
                }
                isFirstInterval = false

                for (time in currentInterval.duration downTo 1) {
                    if (!uiState.isRunning) break

                    uiState = uiState.copy(remainingTime = time)
                    delay(1000)
                }

                if (uiState.isRunning) {
                    if (uiState.currentIntervalIndex < workout.intervals.size - 1) {
                        uiState = uiState.copy(
                            currentIntervalIndex = uiState.currentIntervalIndex + 1,
                            remainingTime = workout.intervals[uiState.currentIntervalIndex + 1].duration
                        )
                    } else {
                        stopWorkout()
                    }
                }
            }
        }
    }

    private fun startGpsTracking() {
        gpsJob = coroutineScope.launch {
            while (uiState.isRunning) {
                delay(2000)

                if (uiState.isRunning) {
                    currentLatitude += Random.nextDouble(-0.0003, 0.0003)
                    currentLongitude += Random.nextDouble(-0.0003, 0.0003)

                    val newPoint = GeoPoint(currentLatitude, currentLongitude)
                    val updatedTrack = uiState.gpsTrack + newPoint

                    uiState = uiState.copy(gpsTrack = updatedTrack)
                }
            }
        }
    }

    fun cleanup() {
        stopWorkout()
        soundManager.release()
    }
}

@Composable
fun rememberWorkoutViewModel(): WorkoutViewModel {
    val context = LocalContext.current
    val soundManager = remember { SoundManager(context) }
    val viewModel = remember { WorkoutViewModel(soundManager) }

    androidx.compose.runtime.DisposableEffect(viewModel) {
        onDispose {
            viewModel.cleanup()
        }
    }

    return viewModel
}