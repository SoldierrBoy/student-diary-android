package com.mobileapp.studentdiary.data.grades

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GradeDao {

    @Query("SELECT * FROM grades WHERE subjectId = :subjectId ORDER BY date DESC")
    fun getGradesBySubject(subjectId: Long): Flow<List<GradeEntity>>

    @Query("SELECT * FROM grades WHERE id = :id LIMIT 1")
    suspend fun getGradeById(id: Long): GradeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(grade: GradeEntity): Long

    @Update
    suspend fun update(grade: GradeEntity)

    @Delete
    suspend fun delete(grade: GradeEntity)
}
