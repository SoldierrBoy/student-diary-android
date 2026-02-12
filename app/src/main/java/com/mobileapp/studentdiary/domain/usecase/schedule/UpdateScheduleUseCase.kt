package com.mobileapp.studentdiary.domain.usecase.schedule

import com.mobileapp.studentdiary.domain.model.Schedule
import com.mobileapp.studentdiary.domain.repository.ScheduleRepository

class UpdateScheduleUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(schedule: Schedule) {
        repository.updateSchedule(schedule)
    }
}
