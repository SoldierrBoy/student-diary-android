package com.mobileapp.studentdiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileapp.studentdiary.domain.usecase.AddStudyTaskUseCase
import com.mobileapp.studentdiary.domain.usecase.DeleteStudyTaskUseCase
import com.mobileapp.studentdiary.domain.usecase.GetAllStudyTasksUseCase
import com.mobileapp.studentdiary.domain.usecase.UpdateStudyTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudyTaskViewModel(
    private val getAllStudyTasksUseCase: GetAllStudyTasksUseCase,
    private val addStudyTaskUseCase: AddStudyTaskUseCase,
    private val updateStudyTaskUseCase: UpdateStudyTaskUseCase,
    private val deleteStudyTaskUseCase: DeleteStudyTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudyTaskUiState())
    val uiState: StateFlow<StudyTaskUiState> = _uiState.asStateFlow()

    fun onEvent(event: StudyTaskEvent) {
        when (event) {

            is StudyTaskEvent.LoadTasks -> {
                loadTasks()
            }

            is StudyTaskEvent.AddTask -> {
                viewModelScope.launch {
                    addStudyTaskUseCase(event.task)
                    loadTasks()
                }
            }

            is StudyTaskEvent.UpdateTask -> {
                viewModelScope.launch {
                    updateStudyTaskUseCase(event.task)
                    loadTasks()
                }
            }

            is StudyTaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    deleteStudyTaskUseCase(event.taskId)
                    loadTasks()
                }
            }
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val tasks = getAllStudyTasksUseCase()

            _uiState.value = _uiState.value.copy(
                tasks = tasks,
                isLoading = false
            )
        }
    }
}
