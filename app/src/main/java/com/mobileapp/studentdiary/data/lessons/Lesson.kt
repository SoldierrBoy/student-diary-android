package com.mobileapp.studentdiary.domain.model

import java.time.LocalDate

data class Lesson(
    val id: Long,
    val subjectId: Long,
    val title: String,
    val date: LocalDate,
    val grade: Int?,      // null якщо оцінки немає
    val isAbsent: Boolean // true якщо н/в
)
