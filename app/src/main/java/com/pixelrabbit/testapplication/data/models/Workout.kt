package com.pixelrabbit.testapplication.data.models

data class Workout(
    val id: Int,
    val name: String,
    val intervals: List<Interval>
) {
    val totalDuration: Int
        get() = intervals.sumOf { it.duration }

    val totalDurationFormatted: String
        get() = formatDuration(totalDuration)
}

data class Interval(
    val id: Int,
    val duration: Int,
    val type: String
)

fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}