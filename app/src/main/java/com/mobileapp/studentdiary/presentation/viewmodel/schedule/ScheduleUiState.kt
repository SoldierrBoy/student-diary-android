package com.mobileapp.studentdiary.presentation.viewmodel.schedule

import com.mobileapp.studentdiary.domain.model.Subject
import com.mobileapp.studentdiary.domain.model.Schedule
import java.time.LocalDate

data class ScheduleUiState(
    val schedules: List<Schedule> = emptyList(),
    val subjects: List<Subject> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val highlightedScheduleId: Long? = null,
    val showAddDialog: Boolean = false
)