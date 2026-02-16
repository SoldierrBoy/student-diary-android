package com.mobileapp.studentdiary.data.schedule

import com.mobileapp.studentdiary.domain.model.Schedule

object ScheduleMapper {

    fun fromDomain(domain: Schedule): ScheduleEntity = ScheduleEntity(
        id = domain.id,
        subjectId = domain.subjectId,
        date = domain.date,
        startTime = domain.startTime,
        endTime = domain.endTime,
        location = domain.location
    )

    fun toDomain(entity: ScheduleEntity): Schedule = Schedule(
        id = entity.id,
        subjectId = entity.subjectId,
        date = entity.date,
        startTime = entity.startTime,
        endTime = entity.endTime,
        location = entity.location
    )
}
