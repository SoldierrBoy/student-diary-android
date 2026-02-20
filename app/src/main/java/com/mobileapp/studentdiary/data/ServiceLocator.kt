package com.mobileapp.studentdiary.data

import android.content.Context
import androidx.room.Room
import com.mobileapp.studentdiary.data.tasks.StudyTaskRepositoryImpl
import com.mobileapp.studentdiary.data.grades.GradeRepositoryImpl
import com.mobileapp.studentdiary.data.subjects.SubjectRepositoryImpl
import com.mobileapp.studentdiary.data.schedule.ScheduleRepositoryImpl
import com.mobileapp.studentdiary.domain.StudyTaskRepository
import com.mobileapp.studentdiary.domain.repository.GradeRepository
import com.mobileapp.studentdiary.domain.repository.SubjectRepository
import com.mobileapp.studentdiary.domain.repository.ScheduleRepository
import com.mobileapp.studentdiary.data.lessons.LessonRepositoryImpl
import com.mobileapp.studentdiary.domain.repository.LessonRepository

/**
 * Service Locator для Data.
 *
 * Виклик: ServiceLocator.init(context) — з аплікації (Application.onCreate) або перед першим використанням.
 * Потім: ServiceLocator.provideStudyTaskRepository() і/або ServiceLocator.provideGradeRepository() і т.д.
 *
 * Метод для підміни репозиторію в тестах: setRepositoryForTests(...)
 * Метод для очищення ресурсів під час тестів: resetForTests()
 */
object ServiceLocator {

    @Volatile
    private var database: AppDatabase? = null

    @Volatile
    private var studyTaskRepository: StudyTaskRepository? = null

    @Volatile
    private var gradeRepository: GradeRepository? = null

    @Volatile
    private var subjectRepository: SubjectRepository? = null

    @Volatile
    private var scheduleRepository: ScheduleRepository? = null

    @Volatile
    private var lessonRepository: LessonRepository? = null


    private val lock = Any()

    /**
     * Ініціалізує базу та репозиторій. Викликати один раз (наприклад, в Application.onCreate()).
     */
    fun init(context: Context) {
        if (studyTaskRepository == null || gradeRepository == null || subjectRepository == null || scheduleRepository == null) {
            synchronized(lock) {
                if (studyTaskRepository == null || gradeRepository == null|| subjectRepository == null || scheduleRepository == null) {
                    val db = database ?: buildDatabase(context)
                    database = db

                    if (studyTaskRepository == null) {
                        studyTaskRepository = StudyTaskRepositoryImpl(db.studyTaskDao())
                    }

                    if (gradeRepository == null) {
                        gradeRepository = GradeRepositoryImpl(db.gradeDao())
                    }

                    if (subjectRepository == null) {
                        subjectRepository = SubjectRepositoryImpl(db.subjectDao())
                    }

                    if (scheduleRepository == null) {
                        scheduleRepository = ScheduleRepositoryImpl(db.scheduleDao())
                    }
                    if (lessonRepository == null) {
                        lessonRepository = LessonRepositoryImpl(db.lessonDao())
                    }

                }
            }
        }
    }

    private fun buildDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "student_diary_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Повертає StudyTaskRepository. Якщо не ініціалізовано — кинеться помилка з підказкою.
     */
    fun provideStudyTaskRepository(): StudyTaskRepository {
        return studyTaskRepository
            ?: throw IllegalStateException(
                "ServiceLocator not initialized. Call ServiceLocator.init(context) before accessing repository."
            )
    }

    /**
     * Повертає GradeRepository. Якщо не ініціалізовано — кинеться помилка з підказкою.
     */
    fun provideGradeRepository(): GradeRepository {
        return gradeRepository
            ?: throw IllegalStateException(
                "ServiceLocator not initialized. Call ServiceLocator.init(context) before accessing grade repository."
            )
    }

    /**
     * Повертає SubjectRepository. Якщо не ініціалізовано — кинеться помилка з підказкою.
     */
    fun provideSubjectRepository(): SubjectRepository {
        return subjectRepository
            ?: throw IllegalStateException(
                "ServiceLocator not initialized. Call ServiceLocator.init(context) before accessing subject repository."
            )
    }

    /**
     * Повертає ScheduleRepository. Якщо не ініціалізовано — кинеться помилка з підказкою.
     */
    fun provideScheduleRepository(): ScheduleRepository {
        return scheduleRepository
            ?: throw IllegalStateException(
                "ServiceLocator not initialized. Call ServiceLocator.init(context) before accessing schedule repository."
            )
    }
    fun provideLessonRepository(): LessonRepository {
        return lessonRepository
            ?: throw IllegalStateException(
                "ServiceLocator not initialized. Call ServiceLocator.init(context) before accessing lesson repository."
            )
    }

    /**
     * Для тестів: підмінити репозиторій (наприклад, FakeStudyTaskRepository.withSampleData()).
     */
    fun setStudyTaskRepositoryForTests(repo: StudyTaskRepository) {
        studyTaskRepository = repo
    }

    fun setGradeRepositoryForTests(repo: GradeRepository) {
        gradeRepository = repo
    }

    fun setSubjectRepositoryForTests(repo: SubjectRepository) {
        subjectRepository = repo
    }

    fun setScheduleRepositoryForTests(repo: ScheduleRepository) {
        scheduleRepository = repo
    }

    /**
     * Використовувати в тестах, щоб закрити БД і очистити стан.
     */
    fun resetForTests() {
        database?.close()
        database = null
        studyTaskRepository = null
        gradeRepository = null
        subjectRepository = null
        scheduleRepository = null
    }
}
