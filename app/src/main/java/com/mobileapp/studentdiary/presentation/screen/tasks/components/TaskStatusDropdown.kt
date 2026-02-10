package com.mobileapp.studentdiary.presentation.screen.tasks.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.mobileapp.studentdiary.domain.TaskStatus

@Composable
fun TaskStatusDropdown(
    status: TaskStatus,
    onStatusChange: (TaskStatus) -> Unit,
    textColor: Color
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        TextButton(
            onClick = { expanded = true },
            colors = ButtonDefaults.textButtonColors(contentColor = textColor)
        ) {
            Text(status.toUkranianText())
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            TaskStatus.values().forEach { newStatus ->
                DropdownMenuItem(
                    text = { Text(newStatus.toUkranianText()) },
                    onClick = {
                        expanded = false
                        onStatusChange(newStatus)
                    }
                )
            }
        }
    }
}