package com.mobileapp.studentdiary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider

//Імпорти для розкладу
import com.mobileapp.studentdiary.data.FakeScheduleRepository
import com.mobileapp.studentdiary.domain.usecase.schedule.DeleteScheduleUseCase
import com.mobileapp.studentdiary.domain.usecase.schedule.GetScheduleByDateUseCase
import com.mobileapp.studentdiary.domain.usecase.schedule.InsertScheduleUseCase
import com.mobileapp.studentdiary.domain.usecase.schedule.UpdateScheduleUseCase
import com.mobileapp.studentdiary.presentation.viewmodel.schedule.ScheduleViewModel
import com.mobileapp.studentdiary.presentation.viewmodel.schedule.ScheduleViewModelFactory

// Імпорти для Завдань
import com.mobileapp.studentdiary.data.FakeStudyTaskRepository
import com.mobileapp.studentdiary.domain.usecase.AddStudyTaskUseCase
import com.mobileapp.studentdiary.domain.usecase.DeleteStudyTaskUseCase
import com.mobileapp.studentdiary.domain.usecase.GetAllStudyTasksUseCase
import com.mobileapp.studentdiary.domain.usecase.UpdateStudyTaskUseCase
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskViewModel
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskViewModelFactory

// Імпорти для Журналу (Предмети та Оцінки)
import com.mobileapp.studentdiary.data.FakeGradeRepository
import com.mobileapp.studentdiary.data.FakeSubjectRepository
import com.mobileapp.studentdiary.domain.usecase.grade.DeleteGradeUseCase
import com.mobileapp.studentdiary.domain.usecase.grade.GetGradesBySubjectUseCase
import com.mobileapp.studentdiary.domain.usecase.grade.InsertGradeUseCase
import com.mobileapp.studentdiary.domain.usecase.subjects.DeleteSubjectUseCase
import com.mobileapp.studentdiary.domain.usecase.subjects.GetAllSubjectsUseCase
import com.mobileapp.studentdiary.domain.usecase.subjects.InsertSubjectUseCase
import com.mobileapp.studentdiary.presentation.viewmodel.grades.GradesViewModel
import com.mobileapp.studentdiary.presentation.viewmodel.grades.GradesViewModelFactory

// UI Імпорти
import com.mobileapp.studentdiary.presentation.StudentDiaryApp
import com.mobileapp.studentdiary.ui.theme.StudentDiaryTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ==========================================
        // 1. НАЛАШТУВАННЯ ЗАВДАНЬ (Tasks)
        // ==========================================
        val tasksRepository = FakeStudyTaskRepository.withSampleData()
        
        val getAllTasks = GetAllStudyTasksUseCase(tasksRepository)
        val addTask = AddStudyTaskUseCase(tasksRepository)
        val updateTask = UpdateStudyTaskUseCase(tasksRepository)
        val deleteTask = DeleteStudyTaskUseCase(tasksRepository)

        val tasksFactory = StudyTaskViewModelFactory(
            getAllStudyTasksUseCase = getAllTasks,
            addStudyTaskUseCase = addTask,
            updateStudyTaskUseCase = updateTask,
            deleteStudyTaskUseCase = deleteTask
        )

        val tasksViewModel = ViewModelProvider(this, tasksFactory)[StudyTaskViewModel::class.java]

        // ==========================================
        // 2. НАЛАШТУВАННЯ ЖУРНАЛУ (Grades & Subjects)
        // ==========================================
        val subjectRepository = FakeSubjectRepository.withSampleData()
        val gradeRepository = FakeGradeRepository.withSampleData()

        val getAllSubjects = GetAllSubjectsUseCase(subjectRepository)
        val insertSubject = InsertSubjectUseCase(subjectRepository)
        val deleteSubject = DeleteSubjectUseCase(subjectRepository)

        val getGradesBySubject = GetGradesBySubjectUseCase(gradeRepository)
        val insertGrade = InsertGradeUseCase(gradeRepository)
        val deleteGrade = DeleteGradeUseCase(gradeRepository)

        val gradesFactory = GradesViewModelFactory(
            getAllSubjectsUseCase = getAllSubjects,
            insertSubjectUseCase = insertSubject,
            deleteSubjectUseCase = deleteSubject,
            getGradesBySubjectUseCase = getGradesBySubject,
            insertGradeUseCase = insertGrade,
            deleteGradeUseCase = deleteGrade
        )

        val gradesViewModel = ViewModelProvider(this, gradesFactory)[GradesViewModel::class.java]
        // ==========================================
        // 3. НАЛАШТУВАННЯ РОЗКЛАДУ (Schedule)
        // ==========================================
        val scheduleRepository = FakeScheduleRepository.withSampleData()

        val getScheduleByDate = GetScheduleByDateUseCase(scheduleRepository)
        val insertSchedule = InsertScheduleUseCase(scheduleRepository)
        val updateSchedule = UpdateScheduleUseCase(scheduleRepository)
        val deleteSchedule = DeleteScheduleUseCase(scheduleRepository)

        val scheduleFactory = ScheduleViewModelFactory(
            getScheduleByDateUseCase = getScheduleByDate,
            insertScheduleUseCase = insertSchedule,
            updateScheduleUseCase = updateSchedule,
            deleteScheduleUseCase = deleteSchedule,
            getAllSubjectsUseCase = getAllSubjects
        )

        val scheduleViewModel = ViewModelProvider(this, scheduleFactory)[ScheduleViewModel::class.java]


        // ==========================================
        // 4. ЗАПУСК ІНТЕРФЕЙСУ
        // ==========================================
setContent {
            // Зчитуємо системну тему (світла/темна) при першому запуску
            val systemTheme = androidx.compose.foundation.isSystemInDarkTheme()
            
            // Створюємо стан, який може змінюватися
            var isDarkTheme by androidx.compose.runtime.remember { 
                androidx.compose.runtime.mutableStateOf(systemTheme) 
            }

            StudentDiaryTheme(darkTheme = isDarkTheme) {
                StudentDiaryApp(
                    tasksViewModel = tasksViewModel,
                    gradesViewModel = gradesViewModel,
                    scheduleViewModel = scheduleViewModel,
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { isDarkTheme = it } 
                )
            }
        }
    }
}