package com.mobileapp.studentdiary.domain.usecase.subjects

data class SubjectUseCases(
    val getAllSubjects: GetAllSubjectsUseCase,
    val insertSubject: InsertSubjectUseCase,
    val updateSubject: UpdateSubjectUseCase,
    val deleteSubject: DeleteSubjectUseCase
)
