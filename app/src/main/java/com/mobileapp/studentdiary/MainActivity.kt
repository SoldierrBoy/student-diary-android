package com.mobileapp.studentdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import com.mobileapp.studentdiary.data.FakeStudyTaskRepository
import com.mobileapp.studentdiary.domain.usecase.AddStudyTaskUseCase
import com.mobileapp.studentdiary.domain.usecase.DeleteStudyTaskUseCase
import com.mobileapp.studentdiary.domain.usecase.GetAllStudyTasksUseCase
import com.mobileapp.studentdiary.domain.usecase.UpdateStudyTaskUseCase
import com.mobileapp.studentdiary.presentation.StudentDiaryApp
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskViewModel
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskViewModelFactory
import com.mobileapp.studentdiary.ui.theme.StudentDiaryTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = FakeStudyTaskRepository.withSampleData()
        val getAllTasks = GetAllStudyTasksUseCase(repository)
        val addTask = AddStudyTaskUseCase(repository)
        val updateTask = UpdateStudyTaskUseCase(repository)
        val deleteTask = DeleteStudyTaskUseCase(repository)

        val factory = StudyTaskViewModelFactory(
            getAllStudyTasksUseCase = getAllTasks,
            addStudyTaskUseCase = addTask,
            updateStudyTaskUseCase = updateTask,
            deleteStudyTaskUseCase = deleteTask
        )

        val viewModel = ViewModelProvider(this, factory)[StudyTaskViewModel::class.java]

        setContent {

            StudentDiaryTheme {
                StudentDiaryApp(
                    tasksViewModel = viewModel
                )
            }
        }
    }
}