package com.mobileapp.studentdiary.presentation.screen.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobileapp.studentdiary.domain.model.Schedule
import com.mobileapp.studentdiary.presentation.screen.schedule.components.AddScheduleDialog
import com.mobileapp.studentdiary.presentation.screen.schedule.components.DayItem
import com.mobileapp.studentdiary.presentation.screen.schedule.components.ScheduleCard
import com.mobileapp.studentdiary.presentation.viewmodel.schedule.ScheduleEvent
import com.mobileapp.studentdiary.presentation.viewmodel.schedule.ScheduleViewModel
import java.time.LocalDate

@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        val start = state.selectedDate.minusDays(7)
        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {
            items((0..14).toList()) { idx ->
                val date = start.plusDays(idx.toLong())
                DayItem(
                    date = date,
                    isSelected = date == state.selectedDate,
                    onClick = { viewModel.onEvent(ScheduleEvent.SelectDate(date)) }
                )
            }
        }

        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (state.schedules.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("На обрану дату пар немає")
                }
            } else {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp)) {
                    items(state.schedules) { schedule ->
                        val subjectName = viewModel.getSubjectName(schedule.subjectId)
                        ScheduleCard(
                            schedule = schedule,
                            subjectName = subjectName
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

    // FAB
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            onClick = { viewModel.onEvent(ScheduleEvent.OpenAddDialog) },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add schedule")
        }
    }

    // Add dialog
    if (state.showAddDialog) {
        AddScheduleDialog(
            subjects = state.subjects,
            selectedDate = state.selectedDate,
            onDismiss = { viewModel.onEvent(ScheduleEvent.CloseAddDialog) },
            onConfirm = { schedule ->
                viewModel.onEvent(ScheduleEvent.AddSchedule(schedule))
            }
        )
    }
}
