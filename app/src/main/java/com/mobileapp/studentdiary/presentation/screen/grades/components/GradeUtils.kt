package com.mobileapp.studentdiary.presentation.screen.grades.components

import androidx.compose.ui.graphics.Color
import kotlin.math.abs

fun generateColorFromString(input: String): Color {
    val hash = input.hashCode()
    val colors = listOf(
        Color(0xFFE53935), Color(0xFF8E24AA), Color(0xFF3949AB),
        Color(0xFF1E88E5), Color(0xFF00897B), Color(0xFF43A047),
        Color(0xFFFDD835), Color(0xFFFB8C00), Color(0xFF6D4C41)
    )
    return colors[abs(hash) % colors.size]
}
fun calculateAverageGrade(grades: List<com.mobileapp.studentdiary.domain.model.Grade>): Double {
    if (grades.isEmpty()) return 0.0
    
    val sum = grades.sumOf { it.value }
    return sum.toDouble() / grades.size
}