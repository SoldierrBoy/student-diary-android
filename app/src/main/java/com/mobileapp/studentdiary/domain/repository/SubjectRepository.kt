package com.mobileapp.studentdiary.domain.repository

import com.mobileapp.studentdiary.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {

    fun getAllSubjects(): Flow<List<Subject>>

    suspend fun getSubjectById(id: Long): Subject?

    suspend fun insertSubject(subject: Subject)

    suspend fun updateSubject(subject: Subject)

    suspend fun deleteSubject(subject: Subject)
}
