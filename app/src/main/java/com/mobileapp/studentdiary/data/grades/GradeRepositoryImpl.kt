package com.mobileapp.studentdiary.data.grades

import com.mobileapp.studentdiary.data.grades.GradeDao
import com.mobileapp.studentdiary.data.grades.toDomain
import com.mobileapp.studentdiary.data.grades.toEntity
import com.mobileapp.studentdiary.domain.model.Grade
import com.mobileapp.studentdiary.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GradeRepositoryImpl(
    private val dao: GradeDao
) : GradeRepository {

    override fun getGradesBySubject(subjectId: Long): Flow<List<Grade>> =
        dao.getGradesBySubject(subjectId).map { list -> list.map { it.toDomain() } }

    override suspend fun getGradeById(id: Long): Grade? =
        dao.getGradeById(id)?.toDomain()

    override suspend fun insertGrade(grade: Grade) {
        dao.insert(grade.toEntity())
    }

    override suspend fun updateGrade(grade: Grade) {
        dao.update(grade.toEntity())
    }

    override suspend fun deleteGrade(grade: Grade) {
        dao.delete(grade.toEntity())
    }
}
