package com.mobileapp.studentdiary.presentation.viewmodel.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobileapp.studentdiary.domain.usecase.schedule.DeleteScheduleUseCase
import com.mobileapp.studentdiary.domain.usecase.schedule.GetScheduleByDateUseCase
import com.mobileapp.studentdiary.domain.usecase.schedule.InsertScheduleUseCase
import com.mobileapp.studentdiary.domain.usecase.schedule.UpdateScheduleUseCase
import com.mobileapp.studentdiary.domain.usecase.subjects.GetAllSubjectsUseCase

class ScheduleViewModelFactory(
    private val getScheduleByDateUseCase: GetScheduleByDateUseCase,
    private val insertScheduleUseCase: InsertScheduleUseCase,
    private val updateScheduleUseCase: UpdateScheduleUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
    private val getAllSubjectsUseCase: GetAllSubjectsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleViewModel(
                getScheduleByDateUseCase,
                insertScheduleUseCase,
                updateScheduleUseCase,
                deleteScheduleUseCase,
                getAllSubjectsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}