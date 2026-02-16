package com.mobileapp.studentdiary.presentation.viewmodel.grades

import com.mobileapp.studentdiary.domain.model.Grade
import com.mobileapp.studentdiary.domain.model.Subject

data class GradesUiState(
    val subjects: List<Subject> = emptyList(),
    val grades: List<Grade> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)