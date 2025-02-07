package com.example.state.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomModal(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
    onCancel: () -> Unit
) {
    if (isOpen) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White,

            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text =  "Eliminar tarea",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "¿Estás seguro de que deseas eliminar esta tarea?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        OutlinedButton(
                            onClick = {
                                onCancel()
                                onDismiss()
                            },
                            modifier = Modifier.fillMaxWidth(0.4f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color.Gray)
                        ) {
                            Text(text = "Cancelar", color = Color.Gray)
                        }

                        Button(
                            onClick = {
                                onAccept()
                                onDismiss()
                            },
                            modifier = Modifier.fillMaxWidth(0.6f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Aceptar", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
