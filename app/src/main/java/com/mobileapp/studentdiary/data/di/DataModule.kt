package com.mobileapp.studentdiary.data.di

import android.content.Context
import androidx.room.Room
import com.mobileapp.studentdiary.data.AppDatabase
import com.mobileapp.studentdiary.data.tasks.StudyTaskDao
import com.mobileapp.studentdiary.data.tasks.StudyTaskRepositoryImpl
import com.mobileapp.studentdiary.domain.StudyTaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "student_diary_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideStudyTaskDao(db: AppDatabase): StudyTaskDao = db.studyTaskDao()

    @Provides
    @Singleton
    fun provideStudyTaskRepository(dao: StudyTaskDao): StudyTaskRepository =
        StudyTaskRepositoryImpl(dao)
}
