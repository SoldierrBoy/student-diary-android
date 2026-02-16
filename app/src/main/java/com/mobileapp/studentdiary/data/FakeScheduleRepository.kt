package com.mobileapp.studentdiary.data

import com.mobileapp.studentdiary.domain.model.Schedule
import com.mobileapp.studentdiary.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalTime

class FakeScheduleRepository : ScheduleRepository {

    private val scheduleFlow = MutableStateFlow<List<Schedule>>(emptyList())
    private var currentId = 1L

    override fun getScheduleByDate(date: LocalDate): Flow<List<Schedule>> {
        return scheduleFlow.map { allSchedules ->
            allSchedules.filter { it.date == date }
                .sortedBy { it.startTime }
        }
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        val newEntry = if (schedule.id == 0L) schedule.copy(id = currentId++) else schedule
        scheduleFlow.value = scheduleFlow.value + newEntry
    }

    override suspend fun updateSchedule(schedule: Schedule) {
        scheduleFlow.value = scheduleFlow.value.map {
            if (it.id == schedule.id) schedule else it
        }
    }

    override suspend fun deleteSchedule(schedule: Schedule) {
        scheduleFlow.value = scheduleFlow.value.filter { it.id != schedule.id }
    }

    companion object {
        fun withSampleData(): FakeScheduleRepository {
            val repository = FakeScheduleRepository()
            
            val today = LocalDate.now()
            val tomorrow = today.plusDays(1)
            
            repository.scheduleFlow.value = listOf(
                Schedule(
                    id = 1L,
                    subjectId = 1L, 
                    date = today,
                    startTime = LocalTime.of(8, 30),
                    endTime = LocalTime.of(10, 0),
                    location = "Ауд. 302"
                ),
                Schedule(
                    id = 2L,
                    subjectId = 2L, 
                    date = today,
                    startTime = LocalTime.of(10, 15),
                    endTime = LocalTime.of(11, 45),
                    location = "Лаб. 12"
                ),
                Schedule(
                    id = 3L,
                    subjectId = 3L, 
                    date = tomorrow,
                    startTime = LocalTime.of(9, 0),
                    endTime = LocalTime.of(10, 30),
                    location = "Ауд. 215"
                ),
                Schedule(
                    id = 4L,
                    subjectId = 1L, 
                    date = tomorrow,
                    startTime = LocalTime.of(10, 45),
                    endTime = LocalTime.of(12, 15),
                    location = "Ауд. 302"
                )
            )
            repository.currentId = 5L
            return repository
        }
    }
}