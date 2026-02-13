package com.mobileapp.studentdiary.data.subjects

import com.mobileapp.studentdiary.domain.model.Subject
import com.mobileapp.studentdiary.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SubjectRepositoryImpl(
    private val dao: SubjectDao
) : SubjectRepository {

    override fun getAllSubjects(): Flow<List<Subject>> =
        dao.getAllSubjects().map { list -> list.map { SubjectMapper.toDomain(it) } }

    override suspend fun getSubjectById(id: Long): Subject? =
        dao.getSubjectById(id)?.let { SubjectMapper.toDomain(it) }

    override suspend fun insertSubject(subject: Subject) {
        dao.insert(SubjectMapper.fromDomain(subject))
    }

    override suspend fun updateSubject(subject: Subject) {
        dao.update(SubjectMapper.fromDomain(subject))
    }

    override suspend fun deleteSubject(subject: Subject) {
        dao.delete(SubjectMapper.fromDomain(subject))
    }
}
