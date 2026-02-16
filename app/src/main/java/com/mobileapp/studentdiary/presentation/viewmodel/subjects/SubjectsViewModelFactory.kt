package com.mobileapp.studentdiary.presentation.viewmodel.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobileapp.studentdiary.domain.usecase.subjects.SubjectUseCases

class SubjectsViewModelFactory(
    private val useCases: SubjectUseCases
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SubjectsViewModel(useCases) as T
    }
}
