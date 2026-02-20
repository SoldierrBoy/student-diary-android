package com.mobileapp.studentdiary.domain.repository

import com.mobileapp.studentdiary.domain.model.Lesson
import kotlinx.coroutines.flow.Flow

interface LessonRepository {

    fun getLessonsBySubject(subjectId: Long): Flow<List<Lesson>>

    suspend fun insertLesson(lesson: Lesson)

    suspend fun updateLesson(lesson: Lesson)

    suspend fun deleteLesson(lesson: Lesson)
}
