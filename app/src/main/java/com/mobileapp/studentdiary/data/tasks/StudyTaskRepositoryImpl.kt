package com.mobileapp.studentdiary.data.tasks

import com.mobileapp.studentdiary.domain.StudyTask
import com.mobileapp.studentdiary.domain.StudyTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudyTaskRepositoryImpl(
    private val dao: StudyTaskDao
) : StudyTaskRepository {

    override suspend fun getAllTasks(): List<StudyTask> = withContext(Dispatchers.IO) {
        dao.getAllTasks().map { StudyTaskMapper.toDomain(it) }
    }

    override suspend fun getTasksBySubject(subjectId: Long): List<StudyTask> = withContext(Dispatchers.IO) {
        dao.getTasksBySubject(subjectId).map { StudyTaskMapper.toDomain(it) }
    }

    override suspend fun addTask(task: StudyTask) = withContext(Dispatchers.IO) {
        dao.insert(StudyTaskMapper.fromDomain(task))
    }

    override suspend fun updateTask(task: StudyTask) = withContext(Dispatchers.IO) {
        dao.update(StudyTaskMapper.fromDomain(task))
    }

    override suspend fun deleteTask(taskId: Long) = withContext(Dispatchers.IO) {
        dao.deleteById(taskId)
    }
}
