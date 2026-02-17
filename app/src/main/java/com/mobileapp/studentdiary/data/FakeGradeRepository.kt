package com.mobileapp.studentdiary.data

import com.mobileapp.studentdiary.domain.model.Grade
import com.mobileapp.studentdiary.domain.repository.GradeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class FakeGradeRepository : GradeRepository {

    private val gradesFlow = MutableStateFlow<List<Grade>>(emptyList())
    private var currentId = 1L

    override fun getGradesBySubject(subjectId: Long): Flow<List<Grade>> {
        return gradesFlow.map { grades ->
            grades.filter { it.subjectId == subjectId }
        }
    }

    // 👈 Додано метод, якого не вистачало
    override suspend fun getGradeById(id: Long): Grade? {
        return gradesFlow.value.find { it.id == id }
    }

    override suspend fun insertGrade(grade: Grade) {
        val newGrade = if (grade.id == 0L) grade.copy(id = currentId++) else grade
        gradesFlow.value = gradesFlow.value + newGrade
    }

    override suspend fun updateGrade(grade: Grade) {
        gradesFlow.value = gradesFlow.value.map {
            if (it.id == grade.id) grade else it
        }
    }

    override suspend fun deleteGrade(grade: Grade) {
        gradesFlow.value = gradesFlow.value.filter { it.id != grade.id }
    }

    companion object {
        fun withSampleData(): FakeGradeRepository {
            return FakeGradeRepository().apply {
                gradesFlow.value = listOf(
                    Grade(id = 1L, subjectId = 1L, value = 95, date = LocalDate.now().minusDays(2)),
                    Grade(id = 2L, subjectId = 1L, value = 88, date = LocalDate.now().minusDays(1)),
                    Grade(id = 3L, subjectId = 2L, value = 100, date = LocalDate.now())
                )
                currentId = 4L
            }
        }
    }
}