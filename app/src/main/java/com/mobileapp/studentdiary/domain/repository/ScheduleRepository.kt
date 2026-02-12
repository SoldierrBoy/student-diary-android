package com.mobileapp.studentdiary.domain.repository

import com.mobileapp.studentdiary.domain.model.Schedule
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ScheduleRepository {

    fun getScheduleByDate(date: LocalDate): Flow<List<Schedule>>

    suspend fun insertSchedule(schedule: Schedule)

    suspend fun updateSchedule(schedule: Schedule)

    suspend fun deleteSchedule(schedule: Schedule)
}
