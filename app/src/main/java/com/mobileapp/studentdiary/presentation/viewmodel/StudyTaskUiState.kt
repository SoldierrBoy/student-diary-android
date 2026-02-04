package com.mobileapp.studentdiary.presentation.viewmodel

import com.mobileapp.studentdiary.domain.StudyTask

data class StudyTaskUiState(
    val tasks: List<StudyTask> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
