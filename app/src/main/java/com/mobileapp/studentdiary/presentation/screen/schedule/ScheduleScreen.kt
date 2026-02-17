package com.mobileapp.studentdiary.presentation.screen.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mobileapp.studentdiary.domain.model.Schedule
import com.mobileapp.studentdiary.presentation.viewmodel.schedule.ScheduleEvent
import com.mobileapp.studentdiary.presentation.viewmodel.schedule.ScheduleViewModel
import com.mobileapp.studentdiary.presentation.screen.schedule.components.AddScheduleDialog
import com.mobileapp.studentdiary.presentation.screen.schedule.components.DayItem
import com.mobileapp.studentdiary.presentation.screen.schedule.components.ScheduleCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel
) {
    val state by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Розклад", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Додати пару")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            DaysRow(
                selectedDate = state.selectedDate,
                onDateSelected = { date ->
                    viewModel.onEvent(ScheduleEvent.SelectDate(date))
                }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (state.schedules.isEmpty()) {
                    Text(
                        text = "На цей день пар немає 🎉",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.schedules, key = { it.id }) { schedule ->
                            val subjectName = viewModel.getSubjectName(schedule.subjectId)
                            ScheduleCard(schedule = schedule, subjectName = subjectName)
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddScheduleDialog(
            subjects = state.subjects,
            selectedDate = state.selectedDate,
            onDismiss = { showAddDialog = false },
            onConfirm = { newSchedule -> 
                viewModel.onEvent(ScheduleEvent.AddSchedule(newSchedule))
                showAddDialog = false
            }
        )
    }
}

@Composable
fun DaysRow(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val startDate = remember { LocalDate.now().minusDays(15) }
    val dates = remember { (0..30).map { startDate.plusDays(it.toLong()) } }
    
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = 12)

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(dates) { date ->
            DayItem(
                date = date,
                isSelected = date == selectedDate,
                onClick = { onDateSelected(date) }
            )
        }
    }
}
