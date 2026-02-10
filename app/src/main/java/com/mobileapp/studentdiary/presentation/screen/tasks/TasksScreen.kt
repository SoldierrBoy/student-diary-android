package com.mobileapp.studentdiary.presentation.screen.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskEvent
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskViewModel
import com.mobileapp.studentdiary.presentation.screen.tasks.components.*

@Composable
fun TasksScreen(
    viewModel: StudyTaskViewModel,
    onAddTaskClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(StudyTaskEvent.LoadTasks)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTaskClick
            ) {
                Text("+")
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> LoadingState()
                state.tasks.isEmpty() -> EmptyTasksState()
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.tasks) { task ->
                            TaskItem(
                                task = task,
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}