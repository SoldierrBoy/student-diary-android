package com.mobileapp.studentdiary.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mobileapp.studentdiary.presentation.navigation.Screen
import com.mobileapp.studentdiary.presentation.screen.grades.GradesScreen
import com.mobileapp.studentdiary.presentation.screen.materials.MaterialsScreen
import com.mobileapp.studentdiary.presentation.screen.schedule.ScheduleScreen
import com.mobileapp.studentdiary.presentation.screen.settings.SettingsScreen
import com.mobileapp.studentdiary.presentation.screen.tasks.AddTaskScreen
import com.mobileapp.studentdiary.presentation.screen.tasks.TasksScreen
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskViewModel

@Composable
fun StudentDiaryApp(
    tasksViewModel: StudyTaskViewModel
) {
    val navController = rememberNavController()
    
    val items = listOf(Screen.Grades, Screen.Schedule, Screen.Tasks, Screen.Materials, Screen.Settings)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Grades.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Grades.route) { GradesScreen() }
            composable(Screen.Schedule.route) { ScheduleScreen() }
            composable(Screen.Tasks.route) { TasksScreen(viewModel = tasksViewModel,
                onAddTaskClick = { navController.navigate("add_task") }) }
            composable("add_task") { AddTaskScreen(viewModel = tasksViewModel, onBack = { navController.popBackStack() })}
            composable(Screen.Materials.route) { MaterialsScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}