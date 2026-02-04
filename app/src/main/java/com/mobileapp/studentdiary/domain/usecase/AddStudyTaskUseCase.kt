package com.mobileapp.studentdiary.domain.usecase

import com.mobileapp.studentdiary.domain.StudyTask
import com.mobileapp.studentdiary.domain.StudyTaskRepository

class AddStudyTaskUseCase(
    private val repository: StudyTaskRepository
) {

    suspend operator fun invoke(task: StudyTask) {
        repository.addTask(task)
    }
}
