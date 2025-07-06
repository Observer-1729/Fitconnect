package com.example.fitconnect

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun TaskScreen(viewModel: TaskViewModel) {
//    val taskViewModel: TaskViewModel = viewModel()
    val context = LocalContext.current
//    val viewModel: TaskViewModel = viewModel(
//        factory = TaskViewModelFactory(context.applicationContext as Application))

    val taskList by viewModel.tasks.collectAsStateWithLifecycle(initialValue = emptyList())

    var showDialog by rememberSaveable { mutableStateOf(false) }

    var taskTitle by rememberSaveable { mutableStateOf("") }
    var taskTime by rememberSaveable { mutableStateOf("") }
    var taskTimeInMillis by rememberSaveable { mutableStateOf(0L) }

    val cardcolor = Color(ContextCompat.getColor(context, R.color.card))


    Box(modifier = Modifier.fillMaxSize().background(Color.Black).padding(start = 8.dp, end = 8.dp)){
    Scaffold(
            containerColor = Color.Black,

            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    modifier = Modifier.padding(bottom = 48.dp),
                    containerColor = Color(0xFF9C27B0)// Adjust this padding as needed
                ) {
                    Text("+", fontSize = 32.sp)
                }
            },
        ) { padding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(bottom = 54.dp)
                ) {
                    items(taskList) { task ->
                        SwipeableTaskCard(
                            taskItem = task,
                            // onTaskClick = onTaskClick,
                            onDelete = {viewModel.cancelTaskNotification(context,it.id,it.title)
                                viewModel.deleteTask(it.id) },
                            onComplete = { viewModel.markTaskComplete(it.id)
                            viewModel.cancelTaskNotification(context,it.id,it.title) }
                        )
                    }
               // }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Add Task") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = taskTitle,
                                onValueChange = { taskTitle = it },
                                label = { Text("Task Title") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // Use a button to open the TimePickerDialog
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (taskTime.isEmpty()) "Select Time" else taskTime,
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(onClick = {
                                    showTimePicker(context) { time, millis ->
                                        taskTime = time
                                        taskTimeInMillis = millis
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.AccessTime,
                                        contentDescription = "Select Time",
                                        tint = Color.Gray
                                    )
                                }

                            }
                        }
                    },
                    confirmButton = {
                        Text(
                            text = "Add",
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    if (taskTitle.isNotBlank() && taskTime.isNotBlank()) {
                                        val newTask = TaskItem(
                                            id = (0..Int.MAX_VALUE).random(),
                                            title = taskTitle,
                                            time = taskTime,
                                            timeInMillis = taskTimeInMillis
                                        )
                                        viewModel.addTask(newTask)

                                        // Schedule the notification for this task
                                        viewModel.scheduleTaskNotification(context, newTask)


                                        taskTitle = ""
                                        taskTime = ""
                                        taskTimeInMillis = 0L
                                        showDialog = false
                                    }
                                }
                        )
                    },
                    dismissButton = {
                        Text(
                            text = "Cancel",
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { showDialog = false }
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableTaskCard(
    taskItem: TaskItem,
    onDelete: (TaskItem) -> Unit,
    onComplete: (TaskItem) -> Unit
) {
    val swipeState = rememberSwipeableState(initialValue = 0)
    val width = 300f
    val anchors = mapOf(0f to 0, -width * 0.4f to 1, width * 0.4f to 2)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val cardcolor = Color(ContextCompat.getColor(context, R.color.card))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(vertical = 8.dp)
            .swipeable(
                state = swipeState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
    ) {
        Row(
            modifier = Modifier.matchParentSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF004717)),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF590606)),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = cardcolor),
            modifier = Modifier
                .offset { IntOffset(swipeState.offset.value.toInt(), 0) }
                .fillMaxWidth()
                .clickable { }
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Title with auto-scroll
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(24.dp)
                ) {
                    ScrollingTitleText(text = taskItem.title, completed = taskItem.completed)

                }

                Spacer(modifier = Modifier.width(12.dp))

                // Time stays visible
                Text(
                    text = taskItem.time,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.wrapContentWidth()
                )
            }
        }

    }

    LaunchedEffect(swipeState.currentValue) {
        when (swipeState.currentValue) {
            1 -> onDelete(taskItem)
            2 -> onComplete(taskItem)
        }
        scope.launch { swipeState.snapTo(0) }
    }
}

fun showTimePicker(
    context: Context,
    onTimeSelected: (formattedTime: String, timeInMillis: Long) -> Unit
) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
            val displayCalendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, selectedHour)
                set(Calendar.MINUTE, selectedMinute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val timeInMillis = displayCalendar.timeInMillis

            // Format for displaying: 12-hour with AM/PM
            val isPM = selectedHour >= 12
            val hourFormatted = if (selectedHour % 12 == 0) 12 else selectedHour % 12
            val minuteFormatted = String.format("%02d", selectedMinute)
            val amPm = if (isPM) "PM" else "AM"
            val time = "$hourFormatted:$minuteFormatted $amPm"

            onTimeSelected(time, timeInMillis)
        },
        hour,
        minute,
        true // Use 24-hour picker UI if preferred
    ).show()
}

//It helps me autoscroll the text
@Composable
fun ScrollingTitleText(text: String, completed: Boolean) {
    val scrollOffset = remember { Animatable(0f) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        while (true) {
            val fullWidth = scrollState.maxValue.toFloat()
            scrollOffset.snapTo(0f)
            scrollOffset.animateTo(
                targetValue = fullWidth,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 8000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .graphicsLayer { translationX = -scrollOffset.value }
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.White,
            textDecoration = if (completed) TextDecoration.LineThrough else TextDecoration.None,
            maxLines = 1
        )
    }
}
