package com.mobileapp.studentdiary.presentation.viewmodel.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileapp.studentdiary.domain.usecase.grade.DeleteGradeUseCase
import com.mobileapp.studentdiary.domain.usecase.grade.GetGradesBySubjectUseCase
import com.mobileapp.studentdiary.domain.usecase.grade.InsertGradeUseCase
import com.mobileapp.studentdiary.domain.usecase.subjects.DeleteSubjectUseCase
import com.mobileapp.studentdiary.domain.usecase.subjects.GetAllSubjectsUseCase
import com.mobileapp.studentdiary.domain.usecase.subjects.InsertSubjectUseCase
import com.mobileapp.studentdiary.presentation.viewmodel.grades.GradesEvent
import com.mobileapp.studentdiary.presentation.screen.grades.components.calculateAverageGrade
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GradesViewModel(
    private val getAllSubjectsUseCase: GetAllSubjectsUseCase,
    private val insertSubjectUseCase: InsertSubjectUseCase,
    private val deleteSubjectUseCase: DeleteSubjectUseCase,
    
    private val getGradesBySubjectUseCase: GetGradesBySubjectUseCase,
    private val insertGradeUseCase: InsertGradeUseCase,
    private val deleteGradeUseCase: DeleteGradeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GradesUiState())
    val uiState: StateFlow<GradesUiState> = _uiState.asStateFlow()

    init {
        loadSubjects()
        loadAllGradesForAllSubjects()
    }
    
    fun onEvent(event: GradesEvent) {
        when (event) {
            is GradesEvent.LoadSubjects -> {
                loadSubjects()
            }

            is GradesEvent.AddSubject -> {
                viewModelScope.launch {
                    insertSubjectUseCase(event.subject)
                    loadSubjects()
                }
            }

            is GradesEvent.DeleteSubject -> {
                viewModelScope.launch {
                    deleteSubjectUseCase(event.subject)
                    loadSubjects()
                }
            }

            is GradesEvent.LoadGrades -> {
                loadGrades(event.subjectId)
            }

            is GradesEvent.AddGrade -> {
                viewModelScope.launch {
                    insertGradeUseCase(event.grade)
                    loadGrades(event.grade.subjectId)
                }
            }

            is GradesEvent.DeleteGrade -> {
                viewModelScope.launch {
                    deleteGradeUseCase(event.grade) 
                    
                    _uiState.value = _uiState.value.copy(
                        grades = _uiState.value.grades.filter { it.id != event.grade.id }
                    )
                    
                    loadAllGradesForAllSubjects() 
                }
            }
        }
    }
fun getSubjectName(subjectId: Long): String {
    return uiState.value.subjects.find { it.id == subjectId }?.name ?: "Предмет"
}
fun getAverageForSubject(subjectId: Long, allGrades: List<com.mobileapp.studentdiary.domain.model.Grade>): Double {
    val subjectGrades = allGrades.filter { it.subjectId == subjectId }
    return calculateAverageGrade(subjectGrades)
}
private fun loadSubjects() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            getAllSubjectsUseCase().collect { subjectsList ->
                _uiState.value = _uiState.value.copy(
                    subjects = subjectsList,
                    isLoading = false
                )
            }
        }
    }

private fun loadGrades(subjectId: Long) {
    viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        getGradesBySubjectUseCase(subjectId).collect { gradesList ->
            _uiState.value = _uiState.value.copy(
                grades = (uiState.value.grades + gradesList).distinctBy { it.id },
                isLoading = false
            )
        }
    }
}
    private fun loadAllGradesForAllSubjects() {
    viewModelScope.launch {

        getAllSubjectsUseCase().collect { subjects ->
            subjects.forEach { subject ->

                launch {
                    getGradesBySubjectUseCase(subject.id).collect { newGrades ->

                        _uiState.value = _uiState.value.copy(
                            grades = (_uiState.value.grades + newGrades).distinctBy { it.id }
                        )
                    }
                }
            }
        }
    }
}
}
