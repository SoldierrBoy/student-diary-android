package com.mobileapp.studentdiary.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mobileapp.studentdiary.presentation.screen.grades.GradesScreen
import com.mobileapp.studentdiary.presentation.screen.grades.SubjectDetailScreen
import com.mobileapp.studentdiary.presentation.screen.materials.MaterialsScreen
import com.mobileapp.studentdiary.presentation.screen.schedule.ScheduleScreen
import com.mobileapp.studentdiary.presentation.screen.settings.SettingsScreen
import com.mobileapp.studentdiary.presentation.screen.tasks.AddTaskScreen
import com.mobileapp.studentdiary.presentation.screen.tasks.TasksScreen
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskViewModel
import com.mobileapp.studentdiary.presentation.viewmodel.schedule.ScheduleViewModel
import com.mobileapp.studentdiary.presentation.viewmodel.grades.GradesViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    tasksViewModel: StudyTaskViewModel,
    gradesViewModel: GradesViewModel,
    scheduleViewModel: ScheduleViewModel,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val navigator = AppNavigator(navController)

    NavHost(
        navController = navController,
        startDestination = Screen.Grades.route,
        modifier = modifier
    ) {

        composable(Screen.Grades.route) {
            GradesScreen(
                viewModel = gradesViewModel,
                onSubjectClick = { 
                   subjectId -> navigator.openSubjectDetails(subjectId) 
                }
            )
        }
        composable(
            route = Screen.SubjectDetails.route,
            arguments = listOf(
                navArgument("subjectId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val subjectId = backStackEntry.arguments?.getLong("subjectId") ?: 0L
            
            SubjectDetailScreen(
                subjectId = subjectId,
                viewModel = gradesViewModel,
                onBack = { navigator.popBack() }
            )
        }
        composable(Screen.Schedule.route) {
        ScheduleScreen(viewModel = scheduleViewModel) 
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
            SettingsScreen(
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
        }
    }
}
