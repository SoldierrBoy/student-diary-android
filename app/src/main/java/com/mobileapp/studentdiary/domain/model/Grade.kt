package com.mobileapp.studentdiary.domain.model

import java.time.LocalDate

data class Grade(
    val id: Long,
    val subjectId: Long,
    val value: Int,
    val date: LocalDate
)
