package com.pixelrabbit.testapplication.presentation.workout.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pixelrabbit.testapplication.data.models.formatDuration
import com.pixelrabbit.testapplication.presentation.workout.WorkoutUiState

@Composable
fun TimerTab(
    uiState: WorkoutUiState,
    onStartStopClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentInterval = uiState.workout.intervals.getOrNull(uiState.currentIntervalIndex)
    val totalIntervals = uiState.workout.intervals.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Статус тренировки
        Text(
            text = if (uiState.isRunning) {
                when {
                    currentInterval == null -> "ТРЕНИРОВКА ЗАВЕРШЕНА"
                    currentInterval.type == "work" -> "РАБОТА"
                    currentInterval.type == "rest" -> "ОТДЫХ"
                    else -> "ИНТЕРВАЛ ${uiState.currentIntervalIndex + 1}"
                }
            } else {
                "ГОТОВ К СТАРТУ"
            },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = if (uiState.isRunning) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface
        )

        // Основной таймер с кнопкой
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(200.dp)
        ) {
            // Прогресс-бар вокруг кнопки
            if (uiState.isRunning && currentInterval != null) {
                CircularProgressIndicator(
                    progress = 1f - (uiState.remainingTime.toFloat() / currentInterval.duration.toFloat()),
                    modifier = Modifier.size(200.dp),
                    strokeWidth = 8.dp,
                    strokeCap = StrokeCap.Round,
                    color = if (currentInterval.type == "work") MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondary
                )
            }

            // Кнопка старт/стоп
            Button(
                onClick = onStartStopClick,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (uiState.isRunning) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary
                )
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (uiState.isRunning && currentInterval != null) {
                        Text(
                            text = formatDuration(uiState.remainingTime),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "из ${formatDuration(currentInterval.duration)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    } else {
                        Text(
                            text = if (uiState.isRunning) "СТОП" else "СТАРТ",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Информация о текущем интервале
        if (uiState.isRunning && currentInterval != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Тип",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Text(
                            text = if (currentInterval.type == "work") "Работа" else "Отдых",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Прогресс",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Text(
                            text = "${uiState.currentIntervalIndex + 1}/$totalIntervals",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Общая информация о тренировке
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "ОБЩАЯ ИНФОРМАЦИЯ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Общее время",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Text(
                            text = uiState.workout.totalDurationFormatted,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Интервалов",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Text(
                            text = totalIntervals.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}