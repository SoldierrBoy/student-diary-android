package com.mobileapp.studentdiary.presentation.screen.grades

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobileapp.studentdiary.domain.model.Subject
import com.mobileapp.studentdiary.presentation.screen.grades.components.generateColorFromString
import com.mobileapp.studentdiary.presentation.viewmodel.grades.GradesEvent
import com.mobileapp.studentdiary.presentation.viewmodel.grades.GradesViewModel

@Composable
fun GradesScreen(
    viewModel: GradesViewModel,
    onSubjectClick: (Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

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
                text = "Журнал оцінок",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    state.subjects.isEmpty() -> {
                        Text(
                            text = "Журнал порожній.\nДодайте перший предмет!",
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                                items(state.subjects) { subject ->
                                val avg = viewModel.getAverageForSubject(subject.id, state.grades)

                                SubjectItem(
                                    subject = subject,
                                    averageGrade = avg,
                                    onClick = { onSubjectClick(subject.id) },
                                    onDelete = { viewModel.onEvent(GradesEvent.DeleteSubject(subject)) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddSubjectDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { subjectName ->
                viewModel.onEvent(
                    GradesEvent.AddSubject(
                        Subject(id = 0L, name = subjectName)
                    )
                )
                showAddDialog = false
            }
        )
    }
}

@Composable
fun SubjectItem(
    subject: Subject,
    averageGrade: Double,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val baseColor = remember(subject.name) { generateColorFromString(subject.name) }
    val cardBackgroundColor = baseColor.copy(alpha = 0.2f)

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Змінюємо Row на Column для тексту
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subject.name,
                    color = baseColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                if (averageGrade > 0) {
                    Text(
                        text = "Середній бал: ${"%.1f".format(averageGrade)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = baseColor.copy(alpha = 0.8f)
                    )
                }
            }
            
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Видалити",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
@Composable
fun AddSubjectDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Новий предмет") },
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
                onClick = { if (text.isNotBlank()) onConfirm(text.trim()) },
                enabled = text.isNotBlank()
            ) {
                Text("Додати")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Скасувати")
            }
        }
    )
}
