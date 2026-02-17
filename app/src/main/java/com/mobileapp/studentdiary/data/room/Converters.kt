package com.mobileapp.studentdiary.data.room

import androidx.room.TypeConverter
import com.mobileapp.studentdiary.domain.TaskPriority
import com.mobileapp.studentdiary.domain.TaskStatus
import java.time.LocalDate
import java.time.LocalTime

object Converters {

    @TypeConverter
    @JvmStatic
    fun fromEpochDay(value: Long?): LocalDate? =
        value?.let { LocalDate.ofEpochDay(it) }

    @TypeConverter
    @JvmStatic
    fun toEpochDay(date: LocalDate?): Long? =
        date?.toEpochDay()

    @TypeConverter
    @JvmStatic
    fun fromTaskStatus(value: String?): TaskStatus? =
        value?.let { TaskStatus.valueOf(it) }

    @TypeConverter
    @JvmStatic
    fun toTaskStatus(status: TaskStatus?): String? =
        status?.name

    @TypeConverter
    @JvmStatic
    fun fromTaskPriority(value: String?): TaskPriority? =
        value?.let { TaskPriority.valueOf(it) }

    @TypeConverter
    @JvmStatic
    fun toTaskPriority(priority: TaskPriority?): String? =
        priority?.name

    @TypeConverter
    @JvmStatic
    fun fromLocalTime(value: String?): LocalTime? =
        value?.let { LocalTime.parse(it) }

    @TypeConverter
    @JvmStatic
    fun toLocalTime(time: LocalTime?): String? =
        time?.toString()

    @TypeConverter
    @JvmStatic
    fun weekParityToInt(parity: com.mobileapp.studentdiary.domain.model.WeekParity?): Int? =
        when (parity) {
            com.mobileapp.studentdiary.domain.model.WeekParity.BOTH -> 0
            com.mobileapp.studentdiary.domain.model.WeekParity.NUMERATOR -> 1
            com.mobileapp.studentdiary.domain.model.WeekParity.DENOMINATOR -> 2
            null -> null
        }

    @TypeConverter
    @JvmStatic
    fun intToWeekParity(i: Int?): com.mobileapp.studentdiary.domain.model.WeekParity? =
        when (i) {
            0 -> com.mobileapp.studentdiary.domain.model.WeekParity.BOTH
            1 -> com.mobileapp.studentdiary.domain.model.WeekParity.NUMERATOR
            2 -> com.mobileapp.studentdiary.domain.model.WeekParity.DENOMINATOR
            else -> null
        }

    @TypeConverter
    @JvmStatic
    fun classTypeToInt(type: com.mobileapp.studentdiary.domain.model.ClassType?): Int? =
        when (type) {
            com.mobileapp.studentdiary.domain.model.ClassType.OTHER -> 0
            com.mobileapp.studentdiary.domain.model.ClassType.LECTURE -> 1
            com.mobileapp.studentdiary.domain.model.ClassType.PRACTICE -> 2
            com.mobileapp.studentdiary.domain.model.ClassType.LAB -> 3
            null -> null
        }

    @TypeConverter
    @JvmStatic
    fun intToClassType(i: Int?): com.mobileapp.studentdiary.domain.model.ClassType? =
        when (i) {
            0 -> com.mobileapp.studentdiary.domain.model.ClassType.OTHER
            1 -> com.mobileapp.studentdiary.domain.model.ClassType.LECTURE
            2 -> com.mobileapp.studentdiary.domain.model.ClassType.PRACTICE
            3 -> com.mobileapp.studentdiary.domain.model.ClassType.LAB
            else -> null
        }
}
