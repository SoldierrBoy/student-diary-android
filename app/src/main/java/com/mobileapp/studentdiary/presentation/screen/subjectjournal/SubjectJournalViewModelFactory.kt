package com.mobileapp.studentdiary.presentation.screen.subjectjournal


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobileapp.studentdiary.domain.repository.LessonRepository

class SubjectJournalViewModelFactory(
    private val repository: LessonRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectJournalViewModel::class.java)) {
            return SubjectJournalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
