package com.mobileapp.studentdiary.data.schedule

import com.mobileapp.studentdiary.domain.model.Schedule
import com.mobileapp.studentdiary.domain.model.WeekParity
import com.mobileapp.studentdiary.domain.model.ClassType

object ScheduleMapper {

    fun fromDomain(domain: Schedule): ScheduleEntity = ScheduleEntity(
        id = domain.id,
        subjectId = domain.subjectId,
        date = domain.date,
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
        date = entity.date,
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
