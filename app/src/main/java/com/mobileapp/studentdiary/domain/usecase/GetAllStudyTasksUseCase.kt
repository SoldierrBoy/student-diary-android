package com.mobileapp.studentdiary.domain.usecase

import com.mobileapp.studentdiary.domain.StudyTask
import com.mobileapp.studentdiary.domain.StudyTaskRepository

class GetAllStudyTasksUseCase(
    private val repository: StudyTaskRepository
) {

    suspend operator fun invoke(): List<StudyTask> {
        return repository.getAllTasks()
    }
}
