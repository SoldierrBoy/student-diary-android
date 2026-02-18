package com.mobileapp.studentdiary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mobileapp.studentdiary.data.room.Converters
import com.mobileapp.studentdiary.data.tasks.StudyTaskDao
import com.mobileapp.studentdiary.data.tasks.StudyTaskEntity
import com.mobileapp.studentdiary.data.grades.GradeDao
import com.mobileapp.studentdiary.data.grades.GradeEntity
import com.mobileapp.studentdiary.data.subjects.SubjectDao
import com.mobileapp.studentdiary.data.subjects.SubjectEntity
import com.mobileapp.studentdiary.data.schedule.ScheduleDao
import com.mobileapp.studentdiary.data.schedule.ScheduleEntity

@Database(entities = [StudyTaskEntity::class,
                        GradeEntity::class,
                        SubjectEntity::class,
                        ScheduleEntity::class], version = 7, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studyTaskDao(): StudyTaskDao
    abstract fun gradeDao(): GradeDao
    abstract fun subjectDao(): SubjectDao
    abstract fun scheduleDao(): ScheduleDao
}


