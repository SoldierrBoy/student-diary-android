package com.mobileapp.studentdiary.presentation.screen.subjectjournal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileapp.studentdiary.domain.model.Lesson
import com.mobileapp.studentdiary.domain.repository.LessonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

class SubjectJournalViewModel(
    private val repository: LessonRepository
) : ViewModel() {

    private val _lessons = MutableStateFlow<List<Lesson>>(emptyList())
    val lessons: StateFlow<List<Lesson>> = _lessons.asStateFlow()

    fun observeLessons(subjectId: Long) {
        repository.getLessonsBySubject(subjectId)
            .onEach { _lessons.value = it }
            .launchIn(viewModelScope)
    }

    fun addLesson(
        subjectId: Long,
        title: String,
        date: LocalDate,
        grade: Int?,
        isAbsent: Boolean
    ) {
        viewModelScope.launch {
            repository.insertLesson(
                Lesson(
                    id = 0L,
                    subjectId = subjectId,
                    title = title,
                    date = date,
                    grade = grade,
                    isAbsent = isAbsent
                )
            )
        }
    }

    fun deleteLesson(lesson: Lesson) {
        viewModelScope.launch {
            repository.deleteLesson(lesson)
        }
    }
}
