package com.mobileapp.studentdiary.presentation.screen.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobileapp.studentdiary.domain.model.Schedule
import com.mobileapp.studentdiary.domain.model.ClassType
import com.mobileapp.studentdiary.domain.model.WeekParity
import java.time.format.DateTimeFormatter

@Composable
fun ScheduleCard(schedule: Schedule, subjectName: String) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(60.dp)
            ) {
                Text(
                    text = schedule.startTime.format(timeFormatter),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = schedule.endTime.format(timeFormatter),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .width(2.dp)
                    .height(40.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = subjectName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Row(modifier = Modifier.padding(top = 4.dp)) {
                    SmallBadge(text = classTypeToLabel(schedule.classType))
                    Spacer(modifier = Modifier.width(8.dp))
                    SmallBadge(text = parityToLabel(schedule.weekParity))
                }

                if (!schedule.location.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Аудиторія",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = schedule.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SmallBadge(text: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
        tonalElevation = 0.dp,
        modifier = Modifier
            .height(26.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 8.dp), contentAlignment = Alignment.Center) {
            Text(text = text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
        }
    }
}

private fun classTypeToLabel(classType: ClassType): String {
    return when (classType) {
        ClassType.LECTURE -> "Лекція"
        ClassType.PRACTICE -> "Практика"
        ClassType.LAB -> "Лабораторна"
        ClassType.OTHER -> "Інше"
    }
}

private fun parityToLabel(parity: WeekParity): String {
    return when (parity) {
        WeekParity.NUMERATOR -> "Чисельник"
        WeekParity.DENOMINATOR -> "Знаменник"
        WeekParity.BOTH -> "Обидва"
    }
}
