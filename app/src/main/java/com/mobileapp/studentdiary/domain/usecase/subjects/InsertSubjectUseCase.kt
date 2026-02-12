package com.mobileapp.studentdiary.domain.usecase.subjects

import com.mobileapp.studentdiary.domain.model.Subject
import com.mobileapp.studentdiary.domain.repository.SubjectRepository

class InsertSubjectUseCase(
    private val repository: SubjectRepository
) {
    suspend operator fun invoke(subject: Subject) {
        repository.insertSubject(subject)
    }
}

