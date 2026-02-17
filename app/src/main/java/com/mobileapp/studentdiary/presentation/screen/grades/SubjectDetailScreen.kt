package com.mobileapp.studentdiary.presentation.screen.grades

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close // Використовуємо хрестик
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mobileapp.studentdiary.domain.model.Grade
import com.mobileapp.studentdiary.presentation.screen.grades.components.generateColorFromString
import com.mobileapp.studentdiary.presentation.screen.grades.components.AddGradeDialog
import com.mobileapp.studentdiary.presentation.viewmodel.grades.GradesEvent
import com.mobileapp.studentdiary.presentation.viewmodel.grades.GradesViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDetailScreen(
    subjectId: Long,
    viewModel: GradesViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val subjectName = remember(subjectId) { viewModel.getSubjectName(subjectId) }
    val subjectColor = remember(subjectName) { generateColorFromString(subjectName) }
    
    var showAddGradeDialog by remember { mutableStateOf(false) }

    LaunchedEffect(subjectId) {
        viewModel.onEvent(GradesEvent.LoadGrades(subjectId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Оцінки", style = MaterialTheme.typography.labelMedium)
                        Text(subjectName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = subjectColor.copy(alpha = 0.1f)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddGradeDialog = true },
                containerColor = subjectColor,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Додати оцінку")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = subjectColor)
            } else if (state.grades.isEmpty()) {
                Text(text = "Оцінок ще немає", modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val currentSubjectGrades = state.grades.filter { it.subjectId == subjectId }
                    
                    items(currentSubjectGrades) { grade ->
                        GradeItem(
                            grade = grade,
                            baseColor = subjectColor,
                            onDelete = {
                                viewModel.onEvent(GradesEvent.DeleteGrade(grade))
                            }
                        )
                    }
                }
            }
        }
    }

    if (showAddGradeDialog) {
        AddGradeDialog(
            onDismiss = { showAddGradeDialog = false },
            onConfirm = { value, date -> 
                viewModel.onEvent(
                    GradesEvent.AddGrade(
                        Grade(id = 0L, subjectId = subjectId, value = value, date = date)
                    )
                )
                showAddGradeDialog = false
            }
        )
    }
}

@Composable
fun GradeItem(
    grade: Grade, 
    baseColor: Color,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = baseColor.copy(alpha = 0.15f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = grade.date.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = baseColor.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = grade.value.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = baseColor
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Видалити",
                    tint = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                )
            }
        }
    }
}