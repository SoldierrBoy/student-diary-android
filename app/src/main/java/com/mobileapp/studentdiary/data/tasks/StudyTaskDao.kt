package com.mobileapp.studentdiary.data.tasks

import androidx.room.*

@Dao
interface StudyTaskDao {
    @Query("SELECT * FROM study_tasks")
    suspend fun getAllTasks(): List<StudyTaskEntity>

    @Query("SELECT * FROM study_tasks WHERE subjectId = :subjectId")
    suspend fun getTasksBySubject(subjectId: Long): List<StudyTaskEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: StudyTaskEntity)

    @Update
    suspend fun update(task: StudyTaskEntity)

    @Query("DELETE FROM study_tasks WHERE id = :taskId")
    suspend fun deleteById(taskId: Long)
}
