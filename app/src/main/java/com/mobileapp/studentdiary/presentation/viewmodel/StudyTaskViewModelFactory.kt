package com.mobileapp.studentdiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobileapp.studentdiary.domain.usecase.*

class StudyTaskViewModelFactory(
    private val getAllStudyTasksUseCase: GetAllStudyTasksUseCase,
    private val addStudyTaskUseCase: AddStudyTaskUseCase,
    private val updateStudyTaskUseCase: UpdateStudyTaskUseCase,
    private val deleteStudyTaskUseCase: DeleteStudyTaskUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudyTaskViewModel::class.java)) {
            return StudyTaskViewModel(
                getAllStudyTasksUseCase,
                addStudyTaskUseCase,
                updateStudyTaskUseCase,
                deleteStudyTaskUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}