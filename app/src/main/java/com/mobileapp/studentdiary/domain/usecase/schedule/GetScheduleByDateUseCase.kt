package com.mobileapp.studentdiary.domain.usecase.schedule

import com.mobileapp.studentdiary.domain.repository.ScheduleRepository
import java.time.LocalDate

class GetScheduleByDateUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(date: LocalDate) =
        repository.getScheduleByDate(date)
}
