package com.mobileapp.studentdiary.presentation.viewmodel.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobileapp.studentdiary.domain.usecase.grade.DeleteGradeUseCase
import com.mobileapp.studentdiary.domain.usecase.grade.GetGradesBySubjectUseCase
import com.mobileapp.studentdiary.domain.usecase.grade.InsertGradeUseCase
import com.mobileapp.studentdiary.domain.usecase.subjects.DeleteSubjectUseCase
import com.mobileapp.studentdiary.domain.usecase.subjects.GetAllSubjectsUseCase
import com.mobileapp.studentdiary.domain.usecase.subjects.InsertSubjectUseCase

class GradesViewModelFactory(
    private val getAllSubjectsUseCase: GetAllSubjectsUseCase,
    private val insertSubjectUseCase: InsertSubjectUseCase,
    private val deleteSubjectUseCase: DeleteSubjectUseCase,
    
    private val getGradesBySubjectUseCase: GetGradesBySubjectUseCase,
    private val insertGradeUseCase: InsertGradeUseCase,
    private val deleteGradeUseCase: DeleteGradeUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GradesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GradesViewModel(
                getAllSubjectsUseCase,
                insertSubjectUseCase,
                deleteSubjectUseCase,
                getGradesBySubjectUseCase,
                insertGradeUseCase,
                deleteGradeUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}