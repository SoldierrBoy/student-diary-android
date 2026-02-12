package com.mobileapp.studentdiary.domain.usecase.subjects

import com.mobileapp.studentdiary.domain.model.Subject
import com.mobileapp.studentdiary.domain.repository.SubjectRepository

class UpdateSubjectUseCase(
    private val repository: SubjectRepository
) {
    suspend operator fun invoke(subject: Subject) {
        repository.updateSubject(subject)
    }
}
