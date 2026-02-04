package com.mobileapp.studentdiary.domain

interface StudyTaskRepository {

    suspend fun getAllTasks(): List<StudyTask>

    suspend fun getTasksBySubject(subjectId: Long): List<StudyTask>

    suspend fun addTask(task: StudyTask)

    suspend fun updateTask(task: StudyTask)

    suspend fun deleteTask(taskId: Long)
}
