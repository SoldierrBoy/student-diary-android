package com.mobileapp.studentdiary.data.grades

import com.mobileapp.studentdiary.domain.model.Grade

fun GradeEntity.toDomain(): Grade = Grade(
    id = id,
    subjectId = subjectId,
    value = value,
    date = date
)

fun Grade.toEntity(): GradeEntity = GradeEntity(
    id = id,
    subjectId = subjectId,
    value = value,
    date = date
)
