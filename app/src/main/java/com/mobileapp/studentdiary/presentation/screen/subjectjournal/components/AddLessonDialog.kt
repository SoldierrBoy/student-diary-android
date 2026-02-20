package com.mobileapp.studentdiary.presentation.screen.subjectjournal.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate

@Composable
fun AddLessonDialog(
    onDismiss: () -> Unit,
    onConfirm: (title: String, date: LocalDate, grade: Int?, isAbsent: Boolean) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now()) }
    var gradeText by remember { mutableStateOf("") }
    var isAbsent by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Нове заняття") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Тип (лаб1 / пр / кр)") }
                )

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
        },
        confirmButton = {
            Button(
                onClick = {
                    val grade = gradeText.toIntOrNull()
                    onConfirm(title, date, grade, isAbsent)
                }
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
