package com.mobileapp.studentdiary.data.schedule

import com.mobileapp.studentdiary.domain.model.Schedule
import com.mobileapp.studentdiary.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class ScheduleRepositoryImpl(
    private val dao: ScheduleDao
) : ScheduleRepository {

    override fun getScheduleByDate(date: LocalDate): Flow<List<Schedule>> =
        dao.getScheduleByDate(date).map { list -> list.map { ScheduleMapper.toDomain(it) } }

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
