package com.mobileapp.studentdiary.data.schedule

import com.mobileapp.studentdiary.domain.model.Schedule
import com.mobileapp.studentdiary.domain.model.WeekParity
import com.mobileapp.studentdiary.domain.model.ClassType
import java.time.DayOfWeek

object ScheduleMapper {

    fun fromDomain(domain: Schedule): ScheduleEntity = ScheduleEntity(
        id = domain.id,
        subjectId = domain.subjectId,
        dayOfWeek = when (domain.dayOfWeek) {
            DayOfWeek.MONDAY -> 1
            DayOfWeek.TUESDAY -> 2
            DayOfWeek.WEDNESDAY -> 3
            DayOfWeek.THURSDAY -> 4
            DayOfWeek.FRIDAY -> 5
            DayOfWeek.SATURDAY -> 6
            DayOfWeek.SUNDAY -> 7
        },
        startTime = domain.startTime,
        endTime = domain.endTime,
        location = domain.location,
        weekParity = when (domain.weekParity) {
            WeekParity.NUMERATOR -> 1
            WeekParity.DENOMINATOR -> 2
            WeekParity.BOTH -> 0
        },
        classType = when (domain.classType) {
            ClassType.LECTURE -> 1
            ClassType.PRACTICE -> 2
            ClassType.LAB -> 3
            ClassType.OTHER -> 0
        },
    )

    fun toDomain(entity: ScheduleEntity): Schedule = Schedule(
        id = entity.id,
        subjectId = entity.subjectId,
        dayOfWeek = when (entity.dayOfWeek) {
            1 -> DayOfWeek.MONDAY
            2 -> DayOfWeek.TUESDAY
            3 -> DayOfWeek.WEDNESDAY
            4 -> DayOfWeek.THURSDAY
            5 -> DayOfWeek.FRIDAY
            6 -> DayOfWeek.SATURDAY
            else -> DayOfWeek.SUNDAY
        },
        startTime = entity.startTime,
        endTime = entity.endTime,
        location = entity.location,
        weekParity = when (entity.weekParity) {
            1 -> WeekParity.NUMERATOR
            2 -> WeekParity.DENOMINATOR
            else -> WeekParity.BOTH
        },
        classType = when (entity.classType) {
            1 -> ClassType.LECTURE
            2 -> ClassType.PRACTICE
            3 -> ClassType.LAB
            else -> ClassType.OTHER
        },
    )
}
