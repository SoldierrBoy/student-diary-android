package com.mobileapp.studentdiary.presentation.screen.subjectjournal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mobileapp.studentdiary.domain.model.Lesson
import com.mobileapp.studentdiary.presentation.screen.subjectjournal.components.AddLessonDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectJournalScreen(
    subjectId: Long,
    viewModel: SubjectJournalViewModel,
    onBack: () -> Unit
) {

    val lessons by viewModel.lessons.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(subjectId) {
        viewModel.observeLessons(subjectId)
    }

    // 🔥 Статистика
    val gradedLessons = lessons.filter { it.grade != null && !it.isAbsent }
    val average = if (gradedLessons.isNotEmpty())
        gradedLessons.map { it.grade!! }.average()
    else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Журнал дисципліни", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Додати заняття")
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            // 📊 Статистика
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Статистика", fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Кількість занять: ${lessons.size}")

                    if (average != null) {
                        Text("Середній бал: ${"%.2f".format(average)}")
                        Text("Прогноз за семестр: ${"%.2f".format(average)}")
                    } else {
                        Text("Середній бал: —")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (lessons.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Поки що немає занять")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(lessons) { lesson ->
                        LessonCard(
                            lesson = lesson,
                            onDelete = { viewModel.deleteLesson(lesson) }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddLessonDialog(
            onDismiss = { showDialog = false },
            onConfirm = { title, date, grade, isAbsent ->
                viewModel.addLesson(subjectId, title, date, grade, isAbsent)
                showDialog = false
            }
        )
    }
}
@Composable
fun LessonCard(
    lesson: Lesson,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = lesson.date.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {

                val gradeText = when {
                    lesson.isAbsent -> "н/в"
                    lesson.grade != null -> lesson.grade.toString()
                    else -> "—"
                }

                Text(
                    text = gradeText,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (lesson.isAbsent)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = onDelete) {
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
