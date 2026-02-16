package com.mobileapp.studentdiary.presentation.viewmodel.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileapp.studentdiary.domain.usecase.subjects.GetAllSubjectsUseCase
import com.mobileapp.studentdiary.domain.usecase.schedule.DeleteScheduleUseCase
import com.mobileapp.studentdiary.domain.usecase.schedule.GetScheduleByDateUseCase
import com.mobileapp.studentdiary.domain.usecase.schedule.InsertScheduleUseCase
import com.mobileapp.studentdiary.domain.usecase.schedule.UpdateScheduleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class ScheduleViewModel(
    private val getScheduleByDateUseCase: GetScheduleByDateUseCase,
    private val insertScheduleUseCase: InsertScheduleUseCase,
    private val updateScheduleUseCase: UpdateScheduleUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
    private val getAllSubjectsUseCase: GetAllSubjectsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    init {
        loadSubjects()
        loadSchedule(_uiState.value.selectedDate)
    }

    fun onEvent(event: ScheduleEvent) {
        when (event) {
            is ScheduleEvent.LoadSchedule -> {
                loadSchedule(event.date)
            }

            is ScheduleEvent.SelectDate -> {
                _uiState.value = _uiState.value.copy(selectedDate = event.date)
                loadSchedule(event.date)
            }

            is ScheduleEvent.AddSchedule -> {
                viewModelScope.launch {
                    insertScheduleUseCase(event.schedule)
                }
            }

            is ScheduleEvent.UpdateSchedule -> {
                viewModelScope.launch {
                    updateScheduleUseCase(event.schedule)
                }
            }

            is ScheduleEvent.DeleteSchedule -> {
                viewModelScope.launch {
                    deleteScheduleUseCase(event.schedule)
                }
            }
        }
    }

    private fun loadSchedule(date: LocalDate) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            getScheduleByDateUseCase(date).collect { scheduleList ->
                _uiState.value = _uiState.value.copy(
                    schedules = scheduleList,
                    isLoading = false
                )
            }
        }
    }
    fun getSubjectName(subjectId: Long): String {
        return uiState.value.subjects.find { it.id == subjectId }?.name ?: "Невідомий предмет"
    }

    private fun loadSubjects() {
        viewModelScope.launch {
            getAllSubjectsUseCase().collect { subjectsList ->
                _uiState.value = _uiState.value.copy(subjects = subjectsList)
            }
        }
    }
}