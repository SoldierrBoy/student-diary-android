package com.mobileapp.studentdiary.data.room

import androidx.room.TypeConverter
import com.mobileapp.studentdiary.domain.TaskPriority
import com.mobileapp.studentdiary.domain.TaskStatus
import java.time.LocalDate

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromEpochDay(value: Long?): LocalDate? = value?.let { LocalDate.ofEpochDay(it) }

    @TypeConverter
    @JvmStatic
    fun toEpochDay(date: LocalDate?): Long? = date?.toEpochDay()

    @TypeConverter
    @JvmStatic
    fun fromTaskStatus(value: String?): TaskStatus? = value?.let { TaskStatus.valueOf(it) }

    @TypeConverter
    @JvmStatic
    fun toTaskStatus(status: TaskStatus?): String? = status?.name

    @TypeConverter
    @JvmStatic
    fun fromTaskPriority(value: String?): TaskPriority? = value?.let { TaskPriority.valueOf(it) }

    @TypeConverter
    @JvmStatic
    fun toTaskPriority(priority: TaskPriority?): String? = priority?.name
}
