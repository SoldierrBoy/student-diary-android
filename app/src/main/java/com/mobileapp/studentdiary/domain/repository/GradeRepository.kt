package com.mobileapp.studentdiary.domain.repository

import com.mobileapp.studentdiary.domain.model.Grade
import kotlinx.coroutines.flow.Flow

interface GradeRepository {

    fun getGradesBySubject(subjectId: Long): Flow<List<Grade>>

    suspend fun getGradeById(id: Long): Grade?

    suspend fun insertGrade(grade: Grade)

    suspend fun updateGrade(grade: Grade)

    suspend fun deleteGrade(grade: Grade)
}
