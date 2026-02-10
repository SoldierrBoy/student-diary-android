package com.mobileapp.studentdiary.presentation.screen.tasks.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobileapp.studentdiary.domain.StudyTask
import com.mobileapp.studentdiary.presentation.viewmodel.StudyTaskEvent

@Composable
fun TaskItem(
    task: StudyTask,
    onEvent: (StudyTaskEvent) -> Unit
) {
    val containerColor = task.priority.getContainerColor()
    val contentColor = task.priority.getContentColor()

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = task.priority.toUkranianText(),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = contentColor.copy(alpha = 0.8f)
                )
            }

            if (!task.description.isNullOrBlank()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor.copy(alpha = 0.9f)
                )
            }

            Divider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = contentColor.copy(alpha = 0.2f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TaskStatusDropdown(
                    status = task.status,
                    onStatusChange = { newStatus ->
                        onEvent(
                            StudyTaskEvent.UpdateTask(
                                task.copy(status = newStatus)
                            )
                        )
                    },
                    textColor = contentColor
                )

                TextButton(
                    onClick = {
                        onEvent(
                            StudyTaskEvent.DeleteTask(task.id)
                        )
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = contentColor)
                ) {
                    Text("Видалити")
                }
            }
        }
    }
}