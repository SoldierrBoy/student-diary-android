package com.mobileapp.studentdiary.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Schedule(
    val id: Long,
    val subjectId: Long,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val location: String?
)
