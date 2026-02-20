package com.mobileapp.studentdiary.presentation.screen.subjectjournal.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobileapp.studentdiary.domain.model.Lesson
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLessonDialog(
    lessonToEdit: Lesson?,              // null = створення
    subjectId: Long,
    onDismiss: () -> Unit,
    onConfirm: (Lesson) -> Unit
) {

    var title by remember { mutableStateOf(lessonToEdit?.title ?: "") }
    var selectedDate by remember { mutableStateOf(lessonToEdit?.date ?: LocalDate.now()) }
    var gradeText by remember { mutableStateOf(lessonToEdit?.grade?.toString() ?: "") }
    var isAbsent by remember { mutableStateOf(lessonToEdit?.isAbsent ?: false) }
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        }
                        showDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Скасувати")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (lessonToEdit == null) "Нове заняття" else "Редагування заняття")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Тип (лаб / пр / кр)") }
                )

                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Обрати дату")
                }

                Text("Обрана дата: $selectedDate")

                OutlinedTextField(
                    value = gradeText,
                    onValueChange = { gradeText = it },
                    label = { Text("Оцінка (0-100)") }
                )

                Row {
                    Checkbox(
                        checked = isAbsent,
                        onCheckedChange = { isAbsent = it }
                    )
                    Text("Відсутній (н/в)")
                }
            }
            val grade = gradeText.toIntOrNull()

            val isGradeValid =
                if (isAbsent) true
                else grade != null && grade in 0..100

            val isFormValid = title.isNotBlank() && isGradeValid
        },

        confirmButton = {
            Button(
                onClick = {
                    val grade = gradeText.toIntOrNull()
                    if (!isAbsent) {
                        if (grade == null || grade !in 0..100) {
                            return@Button
                        }
                    }

                    val lesson = Lesson(
                        id = lessonToEdit?.id ?: 0L,
                        subjectId = subjectId,
                        title = title,
                        date = selectedDate,
                        grade = if (isAbsent) null else grade,
                        isAbsent = isAbsent
                    )

                    onConfirm(lesson)
                }
            ) {
                Text(if (lessonToEdit == null) "Додати" else "Зберегти")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Скасувати")
            }
        }
    )
}