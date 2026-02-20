package com.mobileapp.studentdiary.presentation.screen.subjectdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobileapp.studentdiary.presentation.viewmodel.subjectdetails.SubjectDetailsViewModel

@Composable
fun SubjectDetailsScreen(
    subjectId: Long,
    viewModel: SubjectDetailsViewModel,
    onBack: () -> Unit
) {

    val grades by viewModel.grades.collectAsState()

    var gradeInput by remember { mutableStateOf("") }

    LaunchedEffect(subjectId) {
        viewModel.observeGrades(subjectId)
    }

    // 🔥 Рахуємо середній бал
    val average = remember(grades) {
        if (grades.isNotEmpty()) {
            grades.map { it.value }.average()
        } else {
            0.0
        }
    }

    // 🔥 Простий прогноз = поточний середній
    val forecast = average
    val count = grades.size
    val highest = grades.maxOfOrNull { it.value }
    val lowest = grades.minOfOrNull { it.value }

    Spacer(modifier = Modifier.height(8.dp))


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Журнал предмета",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Середній бал
        Text(
            text = if (grades.isNotEmpty())
                "Середній бал: ${"%.2f".format(average)}"
            else
                "Середній бал: —"
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text("Кількість оцінок: $count")

        if (highest != null && lowest != null) {
            Text("Найвища оцінка: $highest")
            Text("Найнижча оцінка: $lowest")
        }
        // 🔹 Прогноз
        Text(
            text = if (grades.isNotEmpty())
                "Прогноз за семестр: ${"%.2f".format(forecast)}"
            else
                "Прогноз за семестр: —"
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = gradeInput,
            onValueChange = { gradeInput = it },
            label = { Text("Оцінка") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val value = gradeInput.toIntOrNull()

                if (value == null) {
                    return@Button
                }

                if (value !in 0..100) {
                    return@Button
                }

                viewModel.addGrade(subjectId, value)
                gradeInput = ""
            }
        )
        {
            Text("Додати оцінку")
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {
            items(grades) { grade ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Оцінка: ${grade.value} (${grade.date})"
                    )

                    TextButton(
                        onClick = { viewModel.deleteGrade(grade) }
                    ) {
                        Text("Видалити")
                    }
                }
            }

        }
    }
}
