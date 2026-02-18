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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

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
                    _uiState.value = _uiState.value.copy(showAddDialog = false)
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

            is ScheduleEvent.OpenAddDialog -> {
                _uiState.value = _uiState.value.copy(showAddDialog = true)
            }

            is ScheduleEvent.CloseAddDialog -> {
                _uiState.value = _uiState.value.copy(showAddDialog = false)
            }
        }
    }

    private fun loadSchedule(date: LocalDate) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getScheduleByDateUseCase(date).collectLatest { scheduleList ->
                val highlightedId = computeHighlighted(scheduleList, date)
                _uiState.value = _uiState.value.copy(
                    schedules = scheduleList,
                    isLoading = false,
                    highlightedScheduleId = highlightedId
                )
            }
        }
    }

    private fun computeHighlighted(list: List<com.mobileapp.studentdiary.domain.model.Schedule>, date: LocalDate): Long? {
        if (date != LocalDate.now()) return null
        val now = LocalTime.now()
        return list.firstOrNull { s ->
            !now.isBefore(s.startTime) && now.isBefore(s.endTime)
        }?.id
    }

    fun getSubjectName(subjectId: Long): String {
        return uiState.value.subjects.find { it.id == subjectId }?.name ?: "Невідомий предмет"
    }

    private fun loadSubjects() {
        viewModelScope.launch {
            getAllSubjectsUseCase().collectLatest { subjectsList ->
                _uiState.value = _uiState.value.copy(subjects = subjectsList)
            }
        }
    }
}