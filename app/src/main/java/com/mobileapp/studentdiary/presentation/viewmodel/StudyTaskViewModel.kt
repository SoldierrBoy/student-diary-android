package com.mobileapp.studentdiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileapp.studentdiary.domain.StudyTask
import com.mobileapp.studentdiary.domain.usecase.AddStudyTaskUseCase
import com.mobileapp.studentdiary.domain.usecase.DeleteStudyTaskUseCase
import com.mobileapp.studentdiary.domain.usecase.GetAllStudyTasksUseCase
import com.mobileapp.studentdiary.domain.usecase.UpdateStudyTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StudyTaskUiState(
    val tasks: List<StudyTask> = emptyList(),
    val isLoading: Boolean = false
)

class StudyTaskViewModel(
    private val getAllStudyTasksUseCase: GetAllStudyTasksUseCase,
    private val addStudyTaskUseCase: AddStudyTaskUseCase,
    private val updateStudyTaskUseCase: UpdateStudyTaskUseCase,
    private val deleteStudyTaskUseCase: DeleteStudyTaskUseCase
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

    fun addTask(task: StudyTask) {
        viewModelScope.launch {
            addStudyTaskUseCase(task)
            loadTasks()
        }
    }

    fun updateTask(task: StudyTask) {
        viewModelScope.launch {
            updateStudyTaskUseCase(task)
            loadTasks()
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            deleteStudyTaskUseCase(taskId)
            loadTasks()
        }
    }
}
