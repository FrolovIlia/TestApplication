package com.pixelrabbit.testapplication.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixelrabbit.testapplication.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun onWorkoutIdChange(newId: String) {
        _uiState.update { it.copy(workoutId = newId) }
    }

    fun loadWorkout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val id = _uiState.value.workoutId.toIntOrNull() ?: 68
            repository.getWorkout(id).fold(
                onSuccess = { workout ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            workout = workout,
                            navigateToWorkout = true
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Неизвестная ошибка"
                        )
                    }
                }
            )
        }
    }

    fun navigationHandled() {
        _uiState.update { it.copy(navigateToWorkout = false) }
    }

    // Добавьте этот метод для обработки ошибок
    fun onError(errorMessage: String) {
        _uiState.update { it.copy(error = errorMessage) }
    }
}