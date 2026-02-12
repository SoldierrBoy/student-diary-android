package com.mobileapp.studentdiary.domain.usecase.subjects

import com.mobileapp.studentdiary.domain.repository.SubjectRepository

class GetAllSubjectsUseCase(
    private val repository: SubjectRepository
) {
    operator fun invoke() = repository.getAllSubjects()
}
