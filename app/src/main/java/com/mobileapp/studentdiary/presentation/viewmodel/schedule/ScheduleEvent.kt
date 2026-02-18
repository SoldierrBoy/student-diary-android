package com.mobileapp.studentdiary.presentation.viewmodel.schedule

import com.mobileapp.studentdiary.domain.model.Schedule
import java.time.LocalDate

sealed class ScheduleEvent {
    data class LoadSchedule(val date: LocalDate) : ScheduleEvent()

    data class AddSchedule(val schedule: Schedule) : ScheduleEvent()

    data class UpdateSchedule(val schedule: Schedule) : ScheduleEvent()

    data class DeleteSchedule(val schedule: Schedule) : ScheduleEvent()
    
    data class SelectDate(val date: LocalDate) : ScheduleEvent()

    object OpenAddDialog : ScheduleEvent()

    object CloseAddDialog : ScheduleEvent()
}