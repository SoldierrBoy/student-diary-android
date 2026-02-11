package com.mobileapp.studentdiary.presentation.screen.tasks.components

import androidx.compose.ui.graphics.Color
import com.mobileapp.studentdiary.domain.TaskPriority
import com.mobileapp.studentdiary.domain.TaskStatus

fun TaskStatus.toUkranianText(): String {
    return when (this) {
        TaskStatus.TODO -> "Треба зробити"
        TaskStatus.IN_PROGRESS -> "В процесі"
        TaskStatus.DONE -> "Зроблено"
        TaskStatus.REVIEW -> "На перевірці"
        TaskStatus.REVISION -> "Перезахист"
    }
}


fun TaskPriority.getContainerColor(): Color {
    return when (this) {
        TaskPriority.HIGH -> Color(0xFFFFEBEE)
        TaskPriority.MEDIUM -> Color(0xFFFFF3E0)
        TaskPriority.LOW -> Color(0xFFF1F8E9)
    }
}


fun TaskPriority.getContentColor(): Color {
    return when (this) {
        TaskPriority.HIGH -> Color(0xFFB71C1C)
        TaskPriority.MEDIUM -> Color(0xFFE65100)
        TaskPriority.LOW -> Color(0xFF33691E)
    }
}

fun TaskPriority.toColor(): Color {
    return when (this) {
        TaskPriority.HIGH -> Color(0xFFE53935) 
        TaskPriority.MEDIUM -> Color(0xFFFB8C00) 
        TaskPriority.LOW -> Color(0xFF43A047)    
    }
}

fun TaskPriority.toUkranianText(): String {
    return when (this) {
        TaskPriority.HIGH -> "Високий"
        TaskPriority.MEDIUM -> "Середній"
        TaskPriority.LOW -> "Низький"
    }
}