package com.mobileapp.studentdiary.presentation.screen.grades

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mobileapp.studentdiary.domain.model.Subject
import com.mobileapp.studentdiary.presentation.viewmodel.subjects.SubjectsViewModel

@Composable
fun GradesScreen(
    viewModel: SubjectsViewModel,
    onSubjectClick: (Long) -> Unit
) {
    val state by viewModel.state.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var subjectToEdit by remember { mutableStateOf<Subject?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Додати предмет")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Text(
                text = "Журнал предметів",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            if (state.subjects.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Журнал порожній.\nДодайте перший предмет!",
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.subjects) { subject ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSubjectClick(subject.id) }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = subject.name,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Row {
                                    IconButton(
                                        onClick = {
                                            subjectToEdit = subject
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Редагувати"
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            viewModel.deleteSubject(subject)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Видалити",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Діалог створення
    if (showAddDialog) {
        AddSubjectDialog(
            title = "Новий предмет",
            initialValue = "",
            onDismiss = { showAddDialog = false },
            onConfirm = { name ->
                viewModel.addSubject(name)
                showAddDialog = false
            }
        )
    }

    // Діалог редагування
    subjectToEdit?.let { subject ->
        AddSubjectDialog(
            title = "Редагування предмета",
            initialValue = subject.name,
            onDismiss = { subjectToEdit = null },
            onConfirm = { newName ->
                viewModel.updateSubject(subject.copy(name = newName))
                subjectToEdit = null
            }
        )
    }
}

@Composable
fun AddSubjectDialog(
    title: String,
    initialValue: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Назва предмета") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (text.isNotBlank())
                        onConfirm(text.trim())
                },
                enabled = text.isNotBlank()
            ) {
                Text("Зберегти")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Скасувати")
            }
        }
    )
}