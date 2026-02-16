package com.mobileapp.studentdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.mobileapp.studentdiary.data.ServiceLocator
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

        val repository = ServiceLocator.provideStudyTaskRepository()

        val factory = StudyTaskViewModelFactory(
            getAllStudyTasksUseCase = GetAllStudyTasksUseCase(repository),
            addStudyTaskUseCase = AddStudyTaskUseCase(repository),
            updateStudyTaskUseCase = UpdateStudyTaskUseCase(repository),
            deleteStudyTaskUseCase = DeleteStudyTaskUseCase(repository)
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
