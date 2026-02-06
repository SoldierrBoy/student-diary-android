package com.mobileapp.studentdiary.presentation.viewmodel

import com.mobileapp.studentdiary.domain.StudyTask

sealed class StudyTaskEvent {

    object LoadTasks : StudyTaskEvent()

    data class AddTask(
        val task: StudyTask
    ) : StudyTaskEvent()

    data class UpdateTask(
        val task: StudyTask
    ) : StudyTaskEvent()

    data class DeleteTask(
        val taskId: Long
    ) : StudyTaskEvent()
}
