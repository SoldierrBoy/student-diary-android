package com.mobileapp.studentdiary.presentation.viewmodel.grades

import com.mobileapp.studentdiary.domain.model.Grade
import com.mobileapp.studentdiary.domain.model.Subject

sealed class GradesEvent {

    object LoadSubjects : GradesEvent()

    data class AddSubject(val subject: Subject) : GradesEvent()

    data class DeleteSubject(val subject: Subject) : GradesEvent()


    data class LoadGrades(val subjectId: Long) : GradesEvent()

    data class AddGrade(val grade: Grade) : GradesEvent()

    data class DeleteGrade(val grade: Grade) : GradesEvent()
}