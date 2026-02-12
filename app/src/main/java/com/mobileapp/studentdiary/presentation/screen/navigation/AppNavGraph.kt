package com.mobileapp.studentdiary.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mobileapp.studentdiary.presentation.screen.grades.GradesScreen
import com.mobileapp.studentdiary.presentation.screen.materials.MaterialsScreen
import com.mobileapp.studentdiary.presentation.screen.schedule.ScheduleScreen
import com.mobileapp.studentdiary.presentation.screen.settings.SettingsScreen
import com.mobileapp.studentdiary.presentation.screen.tasks.AddTaskScreen
import com.mobileapp.studentdiary.presentation.screen.tasks.TasksScreen
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    tasksViewModel: StudyTaskViewModel,
    modifier: Modifier = Modifier
) {
    val navigator = AppNavigator(navController)

    NavHost(
        navController = navController,
        startDestination = Screen.Grades.route,
        modifier = modifier
    ) {

        composable(Screen.Grades.route) {
            GradesScreen()
        }

        composable(Screen.Schedule.route) {
            ScheduleScreen()
        }

        composable(Screen.Tasks.route) {
            TasksScreen(
                viewModel = tasksViewModel,
                onAddTaskClick = {
                    navigator.openAddTask()
                }
            )
        }

        composable(Screen.AddTask.route) {
            AddTaskScreen(
                viewModel = tasksViewModel,
                onBack = {
                    navigator.popBack()
                }
            )
        }

        composable(Screen.Materials.route) {
            MaterialsScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}
