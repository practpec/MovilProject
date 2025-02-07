package com.example.state.tasks.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.state.tasks.data.model.TaskDTO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskModal(
    isOpen: Boolean,
    idUser: String,
    onDismiss: () -> Unit,
    onCreate: (TaskDTO) -> Unit
) {
    var taskName by remember { mutableStateOf(TextFieldValue("")) }
    var taskDescription by remember { mutableStateOf(TextFieldValue("")) }
    var taskDateLimit by remember { mutableStateOf(TextFieldValue("")) }

    if (isOpen) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Crear nueva tarea") },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = taskName,
                        onValueChange = { taskName = it },
                        label = { Text("Nombre de la tarea") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = taskDateLimit,
                        onValueChange = { taskDateLimit = it },
                        label = { Text("Fecha límite (aaaa-mm-dd)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (taskName.text.isNotEmpty() && taskDescription.text.isNotEmpty() && taskDateLimit.text.isNotEmpty()) {
                            val newTask = TaskDTO(
                                task = taskName.text,
                                description = taskDescription.text,
                                date_limit = taskDateLimit.text,
                                id_user = idUser.toInt()
                            )

                            onCreate(newTask)
                            taskName = TextFieldValue("")
                            taskDescription = TextFieldValue("")
                            taskDateLimit = TextFieldValue("")
                            onDismiss()
                        }
                    }
                ) {
                    Text("Crear tarea")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { onDismiss() }) {
                    Text("Cancelar")
                }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }
}
