package com.mobileapp.studentdiary.presentation.viewmodel.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileapp.studentdiary.domain.usecase.subjects.InsertSubjectUseCase
import com.mobileapp.studentdiary.domain.usecase.subjects.SubjectUseCases
import com.mobileapp.studentdiary.domain.model.Subject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SubjectsViewModel(
    private val useCases: SubjectUseCases
) : ViewModel() {
    fun updateSubject(subject: Subject) {
        viewModelScope.launch {
            useCases.updateSubject(subject)
        }
    }

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            useCases.deleteSubject(subject)
        }
    }

    private val _state = MutableStateFlow(SubjectsUiState())
    val state: StateFlow<SubjectsUiState> = _state.asStateFlow()

    init {
        observeSubjects()
    }

    private fun observeSubjects() {
        useCases.getAllSubjects()
            .onEach { subjects ->
                _state.value = _state.value.copy(subjects = subjects)
            }
            .launchIn(viewModelScope)
    }

    fun addSubject(name: String) {
        viewModelScope.launch {
            useCases.insertSubject(
                Subject(
                    id = 0L,
                    name = name
                )
            )
        }
    }
}
