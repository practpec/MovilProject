package com.example.state.ui.components

import androidx.compose.foundation.layout.*

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.state.tasks.data.model.TaskDTO
import com.example.state.tasks.presentation.TasksViewModel
import java.text.SimpleDateFormat
import java.io.File
import java.util.Locale

@Composable
fun TaskDetailModal(
    isOpen: Boolean,
    task: TaskDTO?,
    onDismiss: () -> Unit,
    tasksViewModel: TasksViewModel
) {
    var audioFile by remember { mutableStateOf<File?>(null) }
    if (isOpen && task != null) {
        LaunchedEffect(task.id_task) {
            tasksViewModel.getLocalAudioFile(task.id_task.toString()) { file ->
                audioFile = file
            }
        }
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cerrar")
                }
            },
            title = { Text("Detalles de la Tarea",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)) },
            text = {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Título:",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = androidx.compose.ui.graphics.Color(0xFF6A1B9A) )
                    Text(task.task)

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Descripción:",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = androidx.compose.ui.graphics.Color(0xFF6A1B9A))
                    Text(task.description)
                    Spacer(modifier = Modifier.height(8.dp))

                    if (audioFile != null) {
                        Button(onClick = { tasksViewModel.playAudioFile(task.id_task.toString()) }) {
                            Text("Reproducir Audio")
                        }
                    } else {
                        Text("No hay audio disponible para esta tarea.")
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Creado el: ",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = androidx.compose.ui.graphics.Color(0xFF6A1B9A)
                    )
                    Text( formatDate(task.createdAt ?: task.createdAt ?: ""))

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Fecha límite:",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = androidx.compose.ui.graphics.Color(0xFF6A1B9A)
                    )
                    Text( formatDate(task.date_limit))
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Estado:",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = androidx.compose.ui.graphics.Color(0xFF6A1B9A)
                    )
                    Text( if (task.status == 1) "Completada" else "Pendiente")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: "Fecha inválida"
    } catch (e: Exception) {
        "Fecha inválida"
    }
}

