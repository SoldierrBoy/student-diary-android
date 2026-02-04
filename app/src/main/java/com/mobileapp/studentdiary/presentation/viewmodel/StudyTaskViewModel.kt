package com.mobileapp.studentdiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileapp.studentdiary.domain.StudyTask
import com.mobileapp.studentdiary.domain.usecase.GetAllStudyTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StudyTaskUiState(
    val tasks: List<StudyTask> = emptyList(),
    val isLoading: Boolean = false
)

class StudyTaskViewModel(
    private val getAllStudyTasksUseCase: GetAllStudyTasksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudyTaskUiState())
    val uiState: StateFlow<StudyTaskUiState> = _uiState.asStateFlow()

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val tasks = getAllStudyTasksUseCase()

            _uiState.value = StudyTaskUiState(
                tasks = tasks,
                isLoading = false
            )
        }
    }
}
