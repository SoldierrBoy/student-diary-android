package com.mobileapp.studentdiary.presentation.screen.schedule.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobileapp.studentdiary.domain.model.ClassType
import com.mobileapp.studentdiary.domain.model.Schedule
import com.mobileapp.studentdiary.domain.model.Subject
import com.mobileapp.studentdiary.domain.model.WeekParity
import java.time.LocalTime
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleDialog(
    subjects: List<Subject>,
    selectedDayOfWeek: DayOfWeek,
    onDismiss: () -> Unit,
    onConfirm: (Schedule) -> Unit
) {
    var subjectExpanded by remember { mutableStateOf(false) }
    var parityExpanded by remember { mutableStateOf(false) }
    var typeExpanded by remember { mutableStateOf(false) }

    var selectedSubject by remember { mutableStateOf(subjects.firstOrNull()) }

    var startTime by remember { mutableStateOf(LocalTime.of(8, 30)) }
    var endTime by remember { mutableStateOf(LocalTime.of(10, 0)) }
    var location by remember { mutableStateOf("") }
    var selectedParity by remember { mutableStateOf(WeekParity.BOTH) }
    var selectedType by remember { mutableStateOf(ClassType.LECTURE) }

    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    val isTimeValid = startTime.isBefore(endTime)
    val canSave = selectedSubject != null && isTimeValid

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Додати пару") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                ExposedDropdownMenuBox(
                    expanded = subjectExpanded,
                    onExpandedChange = { subjectExpanded = !subjectExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedSubject?.name ?: "Виберіть предмет",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Предмет") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subjectExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = subjectExpanded,
                        onDismissRequest = { subjectExpanded = false }
                    ) {
                        if (subjects.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("Немає предметів. Додайте їх у Журналі.") },
                                onClick = { subjectExpanded = false }
                            )
                        } else {
                            subjects.forEach { subject ->
                                DropdownMenuItem(
                                    text = { Text(subject.name) },
                                    onClick = {
                                        selectedSubject = subject
                                        subjectExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = startTime.format(timeFormatter),
                    onValueChange = {},
                    label = { Text("Початок") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showStartTimePicker = true },
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                OutlinedTextField(
                    value = endTime.format(timeFormatter),
                    onValueChange = {},
                    label = { Text("Кінець") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showEndTimePicker = true },
                    enabled = false,
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Аудиторія (необов'язково)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                ExposedDropdownMenuBox(
                    expanded = parityExpanded,
                    onExpandedChange = { parityExpanded = !parityExpanded }
                ) {
                    OutlinedTextField(
                        value = parityToLabel(selectedParity),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Парність тижня") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = parityExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = parityExpanded,
                        onDismissRequest = { parityExpanded = false }
                    ) {
                        WeekParity.entries.forEach { parity ->
                            DropdownMenuItem(
                                text = { Text(parityToLabel(parity)) },
                                onClick = {
                                    selectedParity = parity
                                    parityExpanded = false
                                }
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = typeExpanded,
                    onExpandedChange = { typeExpanded = !typeExpanded }
                ) {
                    OutlinedTextField(
                        value = classTypeToLabel(selectedType),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Тип пари") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = typeExpanded,
                        onDismissRequest = { typeExpanded = false }
                    ) {
                        ClassType.entries.forEach { ct ->
                            DropdownMenuItem(
                                text = { Text(classTypeToLabel(ct)) },
                                onClick = {
                                    selectedType = ct
                                    typeExpanded = false
                                }
                            )
                        }
                    }
                }

                if (!isTimeValid) {
                    Text(
                        text = "Час початку має бути раніше часу кінця",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedSubject?.let { subject ->
                        val newSchedule = Schedule(
                            id = 0L,
                            subjectId = subject.id,
                            dayOfWeek = selectedDayOfWeek,
                            startTime = startTime,
                            endTime = endTime,
                            location = location.takeIf { it.isNotBlank() },
                            weekParity = selectedParity,
                            classType = selectedType
                        )
                        onConfirm(newSchedule)
                    }
                },
                enabled = canSave
            ) {
                Text("Зберегти")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Скасувати") }
        }
    )

    if (showStartTimePicker) {
        val timePickerState = rememberTimePickerState(initialHour = startTime.hour, initialMinute = startTime.minute)
        TimePickerDialog(
            onDismiss = { showStartTimePicker = false },
            onConfirm = {
                startTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                showStartTimePicker = false
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }

    if (showEndTimePicker) {
        val timePickerState = rememberTimePickerState(initialHour = endTime.hour, initialMinute = endTime.minute)
        TimePickerDialog(
            onDismiss = { showEndTimePicker = false },
            onConfirm = {
                endTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                showEndTimePicker = false
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { TextButton(onClick = onConfirm) { Text("OK") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Скасувати") } },
        text = { content() }
    )
}

private fun parityToLabel(parity: WeekParity): String {
    return when (parity) {
        WeekParity.BOTH -> "Обидва тижні"
        WeekParity.NUMERATOR -> "Чисельник"
        WeekParity.DENOMINATOR -> "Знаменник"
    }
}

private fun classTypeToLabel(type: ClassType): String {
    return when (type) {
        ClassType.LECTURE -> "Лекція"
        ClassType.PRACTICE -> "Практика"
        ClassType.LAB -> "Лабораторна"
        ClassType.OTHER -> "Інше"
    }
}
