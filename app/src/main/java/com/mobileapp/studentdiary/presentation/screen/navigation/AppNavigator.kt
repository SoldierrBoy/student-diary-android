package com.mobileapp.studentdiary.presentation.navigation

import androidx.navigation.NavHostController

class AppNavigator(
    private val navController: NavHostController
) {

    // Bottom navigation
    fun openGrades() {
        navController.navigate(Screen.Grades.route)
    }

    fun openSchedule() {
        navController.navigate(Screen.Schedule.route)
    }

    fun openTasks() {
        navController.navigate(Screen.Tasks.route)
    }

    fun openMaterials() {
        navController.navigate(Screen.Materials.route)
    }

    fun openSettings() {
        navController.navigate(Screen.Settings.route)
    }

    // Single screens

    fun openAddTask() {
        navController.navigate(Screen.AddTask.route)
    }

    fun openSubjectDetails(subjectId: Long) {
        navController.navigate(
            Screen.SubjectDetails.createRoute(subjectId)
        )
    }

    fun openAddGrade(subjectId: Long) {
        navController.navigate(
            Screen.AddGrade.createRoute(subjectId)
        )
    }

    fun openAddSchedule() {
        navController.navigate(Screen.AddSchedule.route)
    }

    fun openAddMaterial() {
        navController.navigate(Screen.AddMaterial.route)
    }

    fun popBack() {
        navController.popBackStack()
    }
}
