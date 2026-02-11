package com.mobileapp.studentdiary.data.tasks

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_tasks")
data class StudyTaskEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String?,
    val subjectId: Long,
    val deadlineEpochDay: Long, // LocalDate -> epochDay
    val status: String,         // enum.name
    val priority: String        // enum.name
)
