package com.example.state.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AudioControls(
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onPlayAudio: () -> Unit,

) {

    var hasRecording by remember { mutableStateOf(false) }
    var isRecording by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                onStartRecording()
                isRecording = true
            },
            enabled = !isRecording // Solo habilitado si no se está grabando
        ) {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Grabar"
            )
        }

        Button(
            onClick = {
                onStopRecording()
                isRecording = false
                hasRecording = true
            },
            enabled = isRecording
        ) {
            Icon(
                imageVector = Icons.Default.MicOff,
                contentDescription = "Detener Grabación"
            )
        }

        Button(
            onClick = { onPlayAudio() },
            enabled = hasRecording
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Reproducir"
            )
        }

    }
}
