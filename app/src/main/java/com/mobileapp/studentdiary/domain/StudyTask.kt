package com.mobileapp.studentdiary.domain

import java.time.LocalDate

data class StudyTask(
    val id: Long,
    val title: String,
    val description: String?,
    val subjectId: Long,
    val deadline: LocalDate,
    val status: TaskStatus,
    val priority: TaskPriority
)
