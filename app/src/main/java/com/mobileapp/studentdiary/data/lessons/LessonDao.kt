package com.mobileapp.studentdiary.data.lessons

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDao {

    @Query("SELECT * FROM lessons WHERE subjectId = :subjectId ORDER BY date ASC")
    fun getLessonsBySubject(subjectId: Long): Flow<List<LessonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lesson: LessonEntity): Long

    @Update
    suspend fun update(lesson: LessonEntity)

    @Delete
    suspend fun delete(lesson: LessonEntity)
}
