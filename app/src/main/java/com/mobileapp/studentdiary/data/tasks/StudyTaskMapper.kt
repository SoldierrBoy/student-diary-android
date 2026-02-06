package com.mobileapp.studentdiary.data.tasks

import com.mobileapp.studentdiary.domain.StudyTask
import com.mobileapp.studentdiary.domain.TaskPriority
import com.mobileapp.studentdiary.domain.TaskStatus
import java.time.LocalDate

object StudyTaskMapper {
    fun fromDomain(domain: StudyTask): StudyTaskEntity = StudyTaskEntity(
        id = domain.id,
        title = domain.title,
        description = domain.description,
        subjectId = domain.subjectId,
        deadlineEpochDay = domain.deadline.toEpochDay(),
        status = domain.status.name,
        priority = domain.priority.name
    )

    fun toDomain(entity: StudyTaskEntity): StudyTask = StudyTask(
        id = entity.id,
        title = entity.title,
        description = entity.description,
        subjectId = entity.subjectId,
        deadline = LocalDate.ofEpochDay(entity.deadlineEpochDay),
        status = TaskStatus.valueOf(entity.status),
        priority = TaskPriority.valueOf(entity.priority)
    )
}
