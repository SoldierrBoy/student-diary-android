package com.mobileapp.studentdiary.domain.model

import java.time.LocalDate
import java.time.LocalTime

enum class WeekParity { NUMERATOR, DENOMINATOR, BOTH }
enum class ClassType { LECTURE, PRACTICE, LAB, OTHER }

data class Schedule(
    val id: Long,
    val subjectId: Long,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val location: String?,
    val weekParity: WeekParity = WeekParity.BOTH,
    val classType: ClassType = ClassType.OTHER
)
