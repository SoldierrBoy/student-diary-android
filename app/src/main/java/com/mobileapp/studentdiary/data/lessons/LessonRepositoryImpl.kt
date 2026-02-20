package com.mobileapp.studentdiary.data.lessons

import com.mobileapp.studentdiary.domain.model.Lesson
import com.mobileapp.studentdiary.domain.repository.LessonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LessonRepositoryImpl(
    private val dao: LessonDao
) : LessonRepository {

    override fun getLessonsBySubject(subjectId: Long): Flow<List<Lesson>> {
        return dao.getLessonsBySubject(subjectId)
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertLesson(lesson: Lesson) {
        dao.insert(lesson.toEntity())
    }

    override suspend fun updateLesson(lesson: Lesson) {
        dao.update(lesson.toEntity())
    }

    override suspend fun deleteLesson(lesson: Lesson) {
        dao.delete(lesson.toEntity())
    }
}
