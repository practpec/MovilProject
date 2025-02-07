package com.example.state.tasks.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.state.ui.components.FilterDropdownMenu
import com.example.state.ui.components.FontBackground
import com.example.state.ui.components.TaskCard
import com.example.state.ui.components.CustomModal
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.example.state.tasks.data.model.CreateTaskRequest
import com.example.state.ui.components.CreateTaskModal
import com.example.state.ui.components.TaskDetailModal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(token: String, idUser: String, onLogout: () -> Unit) {
    //val tasksViewModel: TasksViewModel = viewModel()
    val tasksViewModel: TasksViewModel = viewModel(factory = TasksViewModelFactory(LocalContext.current))

    val tasks by tasksViewModel.tasks.observeAsState(emptyList())
    val error by tasksViewModel.error.observeAsState("")
    val selectedTaskId by tasksViewModel.selectedTaskId.observeAsState(null)
    val taskDetail by tasksViewModel.taskDetail.observeAsState(null)

    var filter by remember { mutableStateOf("Todos") }
    var showDeleteModal by remember { mutableStateOf(false) }
    var showCreateTaskModal by remember { mutableStateOf(false) }

    LaunchedEffect(idUser) {
        tasksViewModel.loadTasks(idUser)
    }

    val filteredTasks = when (filter) {
        "Pendientes" -> tasks.filter { it.status == 0 }
        "Completados" -> tasks.filter { it.status == 1 }
        else -> tasks
    }



    FontBackground(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TopAppBar(
                title = { Text("Tasks") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
                actions = {
                    FilterDropdownMenu(onFilterSelected = { selectedFilter ->
                        filter = selectedFilter
                    })
                    FloatingActionButton(
                        onClick = { showCreateTaskModal = true },
                        containerColor = Color.Transparent,
                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { onLogout() }) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp, start = 16.dp, end = 16.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                if (filteredTasks.isEmpty()) {
                    if (error.isNotEmpty()) {
                        Text(text = "Error: $error")
                    } else {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(filteredTasks) { task ->
                            TaskCard(
                                item = task.task,
                                date_limit = task.date_limit,
                                isChecked = task.status == 1,
                                onCheckedChange = { isChecked ->
                                    tasksViewModel.toggleTaskCompletion(task.id_task.toString())
                                },
                                onDeleteClick = {
                                    tasksViewModel.selectTaskToDelete(task.id_task)
                                    showDeleteModal = true
                                },
                                onClick = {
                                    tasksViewModel.selectTaskForDetail(task)
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }

        CustomModal(
            isOpen = showDeleteModal,
            onDismiss = {
                tasksViewModel.clearSelectedTask()
                showDeleteModal = false
            },
            onAccept = {
                selectedTaskId?.let { taskId ->
                    tasksViewModel.deleteTask(taskId.toString())
                }
                showDeleteModal = false
            },
            onCancel = {
                tasksViewModel.clearSelectedTask()
                showDeleteModal = false
            }
        )

        TaskDetailModal(
            isOpen = taskDetail != null,
            task = taskDetail,
            onDismiss = {
                tasksViewModel.clearTaskDetail()
            },
            tasksViewModel = tasksViewModel
        )


        CreateTaskModal(
            isOpen = showCreateTaskModal,
            idUser = idUser,
            onDismiss = { showCreateTaskModal = false },
            onCreate = { newTask ->
                val task = CreateTaskRequest(
                    task = newTask.task,
                    description = newTask.description,
                    date_limit = newTask.date_limit,
                    id_user = newTask.id_user
                )
                tasksViewModel.createTask(task)
            },
            onStartRecording = { tasksViewModel.startRecording() },
            onStopRecording = { tasksViewModel.stopRecording() },
            onPlayAudio = { tasksViewModel.playAudio() },
            onDeleteAudio = { tasksViewModel.deleteRecording() }
        )

    }
}
