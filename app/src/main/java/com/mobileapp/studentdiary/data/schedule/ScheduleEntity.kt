package com.mobileapp.studentdiary.data.schedule


import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mobileapp.studentdiary.data.subjects.SubjectEntity
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "schedules",
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["subjectId"]), Index(value = ["date"])]
)
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val subjectId: Long,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val location: String? = null,
    val weekParity: Int, // 0 = BOTH, 1 = NUMERATOR, 2 = DENOMINATOR
    val classType: Int // 0 = OTHER, 1 = LECTURE, 2 = PRACTICE, 3 = LAB
)
