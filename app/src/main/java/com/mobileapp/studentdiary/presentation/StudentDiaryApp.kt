package com.mobileapp.studentdiary.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mobileapp.studentdiary.presentation.navigation.AppNavGraph
import com.mobileapp.studentdiary.presentation.navigation.AppNavigator
import com.mobileapp.studentdiary.presentation.navigation.Screen
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskViewModel
import com.mobileapp.studentdiary.presentation.viewmodel.subjects.SubjectsViewModel

@Composable
fun StudentDiaryApp(
    tasksViewModel: StudyTaskViewModel,
    subjectsViewModel: SubjectsViewModel
) {
    val navController = rememberNavController()
    val navigator = AppNavigator(navController)

    val bottomItems = listOf(
        Screen.Grades,
        Screen.Schedule,
        Screen.Tasks,
        Screen.Materials,
        Screen.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                bottomItems.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            screen.icon?.let {
                                Icon(it, contentDescription = null)
                            }
                        },
                        label = {
                            screen.title?.let {
                                Text(it)
                            }
                        },
                        selected = currentRoute?.startsWith(screen.route) == true,
                        onClick = {
                            when (screen) {
                                Screen.Grades -> navigator.openGrades()
                                Screen.Schedule -> navigator.openSchedule()
                                Screen.Tasks -> navigator.openTasks()
                                Screen.Materials -> navigator.openMaterials()
                                Screen.Settings -> navigator.openSettings()
                                else -> {}
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            tasksViewModel = tasksViewModel,
            subjectsViewModel = subjectsViewModel,
            navigator = navigator,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
