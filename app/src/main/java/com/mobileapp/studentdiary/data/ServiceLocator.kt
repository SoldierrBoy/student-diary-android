package com.mobileapp.studentdiary.data

import android.content.Context
import androidx.room.Room
import com.mobileapp.studentdiary.data.tasks.StudyTaskRepositoryImpl
import com.mobileapp.studentdiary.domain.StudyTaskRepository

/**
 * Service Locator для Data.
 *
 * Виклик: ServiceLocator.init(context) — з аплікації (Application.onCreate) або перед першим використанням.
 * Потім: ServiceLocator.provideStudyTaskRepository()
 *
 * Метод для підміни репозиторію в тестах: setRepositoryForTests(...)
 * Метод для очищення ресурсів під час тестів: resetForTests()
 */
object ServiceLocator {

    @Volatile
    private var database: AppDatabase? = null

    @Volatile
    private var repository: StudyTaskRepository? = null

    private val lock = Any()

    /**
     * Ініціалізує базу та репозиторій. Викликати один раз (наприклад, в Application.onCreate()).
     */
    fun init(context: Context) {
        if (repository == null) {
            synchronized(lock) {
                if (repository == null) {
                    val db = database ?: buildDatabase(context)
                    database = db
                    repository = StudyTaskRepositoryImpl(db.studyTaskDao())
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
     * Повертає репозиторій. Якщо не ініціалізовано — кинеться помилка з підказкою.
     */
    fun provideStudyTaskRepository(): StudyTaskRepository {
        return repository
            ?: throw IllegalStateException(
                "ServiceLocator not initialized. Call ServiceLocator.init(context) before accessing repository."
            )
    }

    /**
     * Для тестів: підмінити репозиторій (наприклад, FakeStudyTaskRepository.withSampleData()).
     */
    fun setRepositoryForTests(repo: StudyTaskRepository) {
        repository = repo
    }

    /**
     * Використовувати в тестах, щоб закрити БД і очистити стан.
     */
    fun resetForTests() {
        database?.close()
        database = null
        repository = null
    }
}
