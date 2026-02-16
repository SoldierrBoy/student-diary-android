package com.mobileapp.studentdiary.presentation.viewmodel.subjects

import com.mobileapp.studentdiary.domain.model.Subject

data class SubjectsUiState(
    val subjects: List<Subject> = emptyList(),
    val isLoading: Boolean = false
)
