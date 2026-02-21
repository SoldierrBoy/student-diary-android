package com.mobileapp.studentdiary.data.schedule

import com.mobileapp.studentdiary.domain.model.Schedule
import com.mobileapp.studentdiary.domain.repository.ScheduleRepository
import com.mobileapp.studentdiary.domain.model.WeekParity
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class ScheduleRepositoryImpl(
    private val dao: ScheduleDao
) : ScheduleRepository {

    private val mutex = Mutex()

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
        if (!schedule.startTime.isBefore(schedule.endTime)) {
            throw IllegalArgumentException("Час початку має бути раніше часу кінця")
        }

        val entity = ScheduleMapper.fromDomain(schedule)

        mutex.withLock {
            val existing = runBlockingGetSchedulesForDay(entity.dayOfWeek, entity.weekParity)
            val conflict = existing.firstOrNull { overlaps(it, entity) }
            if (conflict != null) {
                throw IllegalStateException("Перекриття з існуючою парою (id=${conflict.id})")
            }

            dao.insert(entity)
        }
    }

    override suspend fun updateSchedule(schedule: Schedule) {
        if (!schedule.startTime.isBefore(schedule.endTime)) {
            throw IllegalArgumentException("Час початку має бути раніше часу кінця")
        }

        val entity = ScheduleMapper.fromDomain(schedule)

        mutex.withLock {
            val existing = runBlockingGetSchedulesForDayExcluding(entity.dayOfWeek, entity.weekParity, entity.id)
            val conflict = existing.firstOrNull { overlaps(it, entity) }
            if (conflict != null) {
                throw IllegalStateException("Перекриття з існуючою парою (id=${conflict.id})")
            }
            dao.update(entity)
        }
    }

    override suspend fun deleteSchedule(schedule: Schedule) {
        dao.delete(ScheduleMapper.fromDomain(schedule))
    }

    private fun overlaps(a: ScheduleEntity, b: ScheduleEntity): Boolean {
        return !(a.endTime <= b.startTime || a.startTime >= b.endTime)
    }

    private suspend fun runBlockingGetSchedulesForDay(dayOfWeek: Int, weekParity: Int): List<ScheduleEntity> {
        return dao.getSchedulesForDayAndParity(dayOfWeek, weekParity)
    }

    private suspend fun runBlockingGetSchedulesForDayExcluding(dayOfWeek: Int, weekParity: Int, excludeId: Long): List<ScheduleEntity> {
        return dao.getSchedulesForDayAndParityExcluding(dayOfWeek, weekParity, excludeId)
    }
}
