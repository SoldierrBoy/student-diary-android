package com.mobileapp.studentdiary.domain.usecase.grade

import com.mobileapp.studentdiary.domain.repository.GradeRepository

class GetGradesBySubjectUseCase(
    private val repository: GradeRepository
) {
    operator fun invoke(subjectId: Long) =
        repository.getGradesBySubject(subjectId)
}
