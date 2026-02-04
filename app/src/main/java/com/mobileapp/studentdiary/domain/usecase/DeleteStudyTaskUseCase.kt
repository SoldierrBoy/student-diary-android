package com.mobileapp.studentdiary.domain.usecase

import com.mobileapp.studentdiary.domain.StudyTaskRepository

class DeleteStudyTaskUseCase(
    private val repository: StudyTaskRepository
) {

    suspend operator fun invoke(taskId: Long) {
        repository.deleteTask(taskId)
    }
}
