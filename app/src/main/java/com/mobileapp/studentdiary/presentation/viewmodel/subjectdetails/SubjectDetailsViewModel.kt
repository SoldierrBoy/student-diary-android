package com.mobileapp.studentdiary.presentation.viewmodel.subjectdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileapp.studentdiary.domain.model.Grade
import com.mobileapp.studentdiary.domain.repository.GradeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

class SubjectDetailsViewModel(
    private val repository: GradeRepository
) : ViewModel() {

    private val _grades = MutableStateFlow<List<Grade>>(emptyList())
    val grades: StateFlow<List<Grade>> = _grades.asStateFlow()

    fun observeGrades(subjectId: Long) {
        repository.getGradesBySubject(subjectId)
            .onEach { _grades.value = it }
            .launchIn(viewModelScope)
    }

    fun addGrade(subjectId: Long, value: Int) {
        viewModelScope.launch {
            repository.insertGrade(
                Grade(
                    id = 0L,
                    subjectId = subjectId,
                    value = value,
                    date = LocalDate.now()
                )
            )
        }
    }

    fun deleteGrade(grade: Grade) {
        viewModelScope.launch {
            repository.deleteGrade(grade)
        }
    }
}
