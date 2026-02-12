package com.mobileapp.studentdiary.domain.usecase.subjects

import com.mobileapp.studentdiary.domain.model.Subject
import com.mobileapp.studentdiary.domain.repository.SubjectRepository

class DeleteSubjectUseCase(
    private val repository: SubjectRepository
) {
    suspend operator fun invoke(subject: Subject) {
        repository.deleteSubject(subject)
    }
}
