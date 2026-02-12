package com.mobileapp.studentdiary.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.automirrored.filled.List

sealed class Screen(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {

    // Bottom tabs
    object Grades : Screen(
        route = "grades",
        title = "Журнал",
        icon = Icons.Default.Star
    )

    object Schedule : Screen(
        route = "schedule",
        title = "Розклад",
        icon = Icons.Default.DateRange
    )

    object Tasks : Screen(
        route = "tasks",
        title = "Завдання",
        icon = Icons.AutoMirrored.Filled.List

    )

    object Materials : Screen(
        route = "materials",
        title = "Матеріали",
        icon = Icons.Default.Book
    )

    object Settings : Screen(
        route = "settings",
        title = "Налаштування",
        icon = Icons.Default.Settings
    )

    // Single screens (НЕ таби)

    object AddTask : Screen(
        route = "add_task"
    )

    object SubjectDetails : Screen(
        route = "subject_details/{subjectId}"
    ) {
        fun createRoute(subjectId: Long) =
            "subject_details/$subjectId"
    }

    object AddGrade : Screen(
        route = "add_grade/{subjectId}"
    ) {
        fun createRoute(subjectId: Long) =
            "add_grade/$subjectId"
    }

    object AddSchedule : Screen(
        route = "add_schedule"
    )

    object AddMaterial : Screen(
        route = "add_material"
    )
}
