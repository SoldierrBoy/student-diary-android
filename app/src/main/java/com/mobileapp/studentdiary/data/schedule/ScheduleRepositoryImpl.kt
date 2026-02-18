package com.mobileapp.studentdiary.data.schedule

import com.mobileapp.studentdiary.domain.model.Schedule
import com.mobileapp.studentdiary.domain.repository.ScheduleRepository
import com.mobileapp.studentdiary.domain.model.WeekParity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.filter
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class ScheduleRepositoryImpl(
    private val dao: ScheduleDao
) : ScheduleRepository {

    override fun getScheduleByDate(date: LocalDate): Flow<List<Schedule>> {
        val dayOfWeek = date.dayOfWeek.value
        val parity = computeParity(date)
        return dao.getScheduleByDay(dayOfWeek)
            .map { list ->
                list
                    .map { ScheduleMapper.toDomain(it) }
                    .filter { schedule ->
                        when (schedule.weekParity) {
                            WeekParity.BOTH -> true
                            WeekParity.NUMERATOR -> parity == WeekParity.NUMERATOR
                            WeekParity.DENOMINATOR -> parity == WeekParity.DENOMINATOR
                        }
                    }
            }
    }

    private fun computeParity(date: LocalDate): WeekParity {
        val weekFields = WeekFields.of(Locale.getDefault())
        val weekNumber = date.get(weekFields.weekOfWeekBasedYear())
        return if (weekNumber % 2 == 1) WeekParity.NUMERATOR else WeekParity.DENOMINATOR
    }

    override suspend fun insertSchedule(schedule: Schedule) {
        dao.insert(ScheduleMapper.fromDomain(schedule))
    }

    override suspend fun updateSchedule(schedule: Schedule) {
        dao.update(ScheduleMapper.fromDomain(schedule))
    }

    override suspend fun deleteSchedule(schedule: Schedule) {
        dao.delete(ScheduleMapper.fromDomain(schedule))
    }
}
