package com.mobileapp.studentdiary.data

import com.mobileapp.studentdiary.domain.StudyTask
import com.mobileapp.studentdiary.domain.StudyTaskRepository
import com.mobileapp.studentdiary.domain.TaskPriority
import com.mobileapp.studentdiary.domain.TaskStatus
import kotlinx.coroutines.delay
import java.time.LocalDate

class FakeStudyTaskRepository : StudyTaskRepository {

    private val tasks = mutableListOf<StudyTask>()

    override suspend fun getAllTasks(): List<StudyTask> {
        delay(300)
        return tasks.toList()
    }

    override suspend fun getTasksBySubject(subjectId: Long): List<StudyTask> {
        delay(200)
        return tasks.filter { it.subjectId == subjectId }
    }

    override suspend fun addTask(task: StudyTask) {
        tasks.add(task)
    }

    override suspend fun updateTask(task: StudyTask) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) {
            tasks[index] = task
        }
    }

    override suspend fun deleteTask(taskId: Long) {
        tasks.removeAll { it.id == taskId }
    }

    companion object {
        fun withSampleData(): FakeStudyTaskRepository {
            return FakeStudyTaskRepository().apply {
                tasks.addAll(
                    listOf(
                        StudyTask(
                            id = 1L,
                            title = "Лаба з ООП",
                            description = "Зробити UML діаграму",
                            subjectId = 101L,
                            deadline = LocalDate.now().plusDays(5),
                            status = TaskStatus.IN_PROGRESS,
                            priority = TaskPriority.HIGH
                        ),
                        StudyTask(
                            id = 2L,
                            title = "Підготовка до матану",
                            description = "Повторити інтеграли",
                            subjectId = 102L,
                            deadline = LocalDate.now().plusDays(3),
                            status = TaskStatus.TODO,
                            priority = TaskPriority.MEDIUM
                        )
                    )
                )
            }
        }
    }
}
