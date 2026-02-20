package com.mobileapp.studentdiary.data.lessons

import com.mobileapp.studentdiary.domain.model.Lesson

fun LessonEntity.toDomain(): Lesson {
    return Lesson(
        id = id,
        subjectId = subjectId,
        title = title,
        date = date,
        grade = grade,
        isAbsent = isAbsent
    )
}

fun Lesson.toEntity(): LessonEntity {
    return LessonEntity(
        id = id,
        subjectId = subjectId,
        title = title,
        date = date,
        grade = grade,
        isAbsent = isAbsent
    )
}
