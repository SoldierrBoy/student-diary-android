package com.mobileapp.studentdiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.mobileapp.studentdiary.data.ServiceLocator
import com.mobileapp.studentdiary.domain.usecase.*
import com.mobileapp.studentdiary.domain.usecase.subjects.*
import com.mobileapp.studentdiary.presentation.StudentDiaryApp
import com.mobileapp.studentdiary.presentation.screen.subjectjournal.SubjectJournalViewModel
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskViewModel
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskViewModelFactory
import com.mobileapp.studentdiary.presentation.viewmodel.subjects.SubjectsViewModel
import com.mobileapp.studentdiary.presentation.viewmodel.subjects.SubjectsViewModelFactory
import com.mobileapp.studentdiary.presentation.screen.subjectjournal.SubjectJournalViewModelFactory
import com.mobileapp.studentdiary.ui.theme.StudentDiaryTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ServiceLocator.init(applicationContext)

        // TASKS
        val taskRepository = ServiceLocator.provideStudyTaskRepository()

        val tasksFactory = StudyTaskViewModelFactory(
            getAllStudyTasksUseCase = GetAllStudyTasksUseCase(taskRepository),
            addStudyTaskUseCase = AddStudyTaskUseCase(taskRepository),
            updateStudyTaskUseCase = UpdateStudyTaskUseCase(taskRepository),
            deleteStudyTaskUseCase = DeleteStudyTaskUseCase(taskRepository)
        )

        val tasksViewModel =
            ViewModelProvider(this, tasksFactory)[StudyTaskViewModel::class.java]

        // SUBJECTS
        val subjectRepository = ServiceLocator.provideSubjectRepository()

        val subjectUseCases = SubjectUseCases(
            getAllSubjects = GetAllSubjectsUseCase(subjectRepository),
            insertSubject = InsertSubjectUseCase(subjectRepository),
            updateSubject = UpdateSubjectUseCase(subjectRepository),
            deleteSubject = DeleteSubjectUseCase(subjectRepository)
        )

        val subjectsFactory = SubjectsViewModelFactory(subjectUseCases)

        val subjectsViewModel =
            ViewModelProvider(this, subjectsFactory)[SubjectsViewModel::class.java]

        // LESSONS (Journal Logic)
        val lessonRepository = ServiceLocator.provideLessonRepository()

        val subjectJournalViewModel = ViewModelProvider(
            this,
            SubjectJournalViewModelFactory(lessonRepository)
        )[SubjectJournalViewModel::class.java]

        setContent {
            StudentDiaryTheme {
                StudentDiaryApp(
                    tasksViewModel = tasksViewModel,
                    subjectsViewModel = subjectsViewModel,
                    subjectJournalViewModel = subjectJournalViewModel
                )
            }
        }
    }
}
