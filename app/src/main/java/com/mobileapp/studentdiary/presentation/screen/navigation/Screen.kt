package com.mobileapp.studentdiary.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Grades : Screen("grades", "Журнал", Icons.Default.Star)
    object Schedule : Screen("schedule", "Розклад", Icons.Default.DateRange)
    object Tasks : Screen("tasks", "Завдання", Icons.Default.List)
    object AddTask : Screen("add_task", "Нове", Icons.Default.Add)
    object Materials : Screen("materials", "Матеріали", Icons.Default.Book)
    object Settings : Screen("settings", "Налаштування", Icons.Default.Settings)
}