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

        OutlinedTextField(
            value = gradeInput,
            onValueChange = { gradeInput = it },
            label = { Text("Оцінка") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val value = gradeInput.toIntOrNull()
                if (value != null) {
                    viewModel.addGrade(subjectId, value)
                    gradeInput = ""
                }
            }
        ) {
            Text("Додати оцінку")
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {
            items(grades) { grade ->
                Text(
                    text = "Оцінка: ${grade.value}  (${grade.date})",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
