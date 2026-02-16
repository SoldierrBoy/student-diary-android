package com.mobileapp.studentdiary.presentation.screen.grades

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobileapp.studentdiary.presentation.viewmodel.subjects.SubjectsViewModel

@Composable
fun GradesScreen(
    viewModel: SubjectsViewModel,
    onSubjectClick: (Long) -> Unit
) {

    val state by viewModel.state.collectAsState()

    var newSubjectName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Журнал предметів",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- Додавання предмету ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newSubjectName,
                onValueChange = { newSubjectName = it },
                modifier = Modifier.weight(1f),
                label = { Text("Назва предмета") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (newSubjectName.isNotBlank()) {
                        viewModel.addSubject(newSubjectName.trim())
                        newSubjectName = ""
                    }
                }
            ) {
                Text("Додати")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Список предметів ---
        if (state.subjects.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Поки що немає предметів")
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.subjects) { subject ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            onSubjectClick(subject.id)
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = subject.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
