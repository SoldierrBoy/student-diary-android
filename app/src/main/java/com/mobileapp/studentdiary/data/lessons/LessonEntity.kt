package com.mobileapp.studentdiary.data.lessons

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mobileapp.studentdiary.data.subjects.SubjectEntity
import java.time.LocalDate

@Entity(
    tableName = "lessons",
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["subjectId"])]
)
data class LessonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val subjectId: Long,
    val title: String,
    val date: LocalDate,
    val grade: Int?,      // null якщо немає оцінки
    val isAbsent: Boolean // true якщо н/в
)
