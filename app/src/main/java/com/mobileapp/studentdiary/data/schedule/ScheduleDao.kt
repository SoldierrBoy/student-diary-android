package com.mobileapp.studentdiary.data.schedule


import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedules WHERE dayOfWeek = :dayOfWeek ORDER BY startTime ASC")
    fun getScheduleByDay(dayOfWeek: Int): Flow<List<ScheduleEntity>>

    @Query("""
        SELECT * FROM schedules
        WHERE dayOfWeek = :dayOfWeek
          AND (weekParity = :weekParity OR weekParity = 0 OR :weekParity = 0)
    """)
    suspend fun getSchedulesForDayAndParity(dayOfWeek: Int, weekParity: Int): List<ScheduleEntity>

    @Query("""
        SELECT * FROM schedules
        WHERE dayOfWeek = :dayOfWeek
          AND id != :excludeId
          AND (weekParity = :weekParity OR weekParity = 0 OR :weekParity = 0)
    """)
    suspend fun getSchedulesForDayAndParityExcluding(dayOfWeek: Int, weekParity: Int, excludeId: Long): List<ScheduleEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: ScheduleEntity): Long

    @Update
    suspend fun update(schedule: ScheduleEntity)

    @Delete
    suspend fun delete(schedule: ScheduleEntity)
}
