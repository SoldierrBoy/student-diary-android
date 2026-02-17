package com.mobileapp.studentdiary.data

import com.mobileapp.studentdiary.domain.model.Subject
import com.mobileapp.studentdiary.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSubjectRepository : SubjectRepository {

    private val subjectsFlow = MutableStateFlow<List<Subject>>(emptyList())
    private var currentId = 1L 

    override fun getAllSubjects(): Flow<List<Subject>> {
        return subjectsFlow
    }

    // 👈 Додано метод, якого не вистачало
    override suspend fun getSubjectById(id: Long): Subject? {
        return subjectsFlow.value.find { it.id == id }
    }

    override suspend fun insertSubject(subject: Subject) {
        val newSubject = if (subject.id == 0L) subject.copy(id = currentId++) else subject
        subjectsFlow.value = subjectsFlow.value + newSubject
    }

    override suspend fun updateSubject(subject: Subject) {
        subjectsFlow.value = subjectsFlow.value.map {
            if (it.id == subject.id) subject else it
        }
    }

    override suspend fun deleteSubject(subject: Subject) {
        subjectsFlow.value = subjectsFlow.value.filter { it.id != subject.id }
    }

    companion object {
        fun withSampleData(): FakeSubjectRepository {
            return FakeSubjectRepository().apply {
                subjectsFlow.value = listOf(
                    Subject(id = 1L, name = "Вища математика"),
                    Subject(id = 2L, name = "Програмування (Java)"),
                    Subject(id = 3L, name = "Англійська мова")
                )
                currentId = 4L
            }
        }
    }
}