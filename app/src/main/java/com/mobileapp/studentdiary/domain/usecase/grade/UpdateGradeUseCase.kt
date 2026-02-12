package com.mobileapp.studentdiary.domain.usecase.grade

import com.mobileapp.studentdiary.domain.model.Grade
import com.mobileapp.studentdiary.domain.repository.GradeRepository

class UpdateGradeUseCase(
    private val repository: GradeRepository
) {
    suspend operator fun invoke(grade: Grade) {
        repository.updateGrade(grade)
    }
}
