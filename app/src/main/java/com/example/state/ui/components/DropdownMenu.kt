package com.example.state.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterDropdownMenu() {
    val isDropDownExpanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("All") }
    val filterOptions = listOf("All", "Pending", "Completed")

    Box(
        modifier = Modifier
            .clickable { isDropDownExpanded.value = !isDropDownExpanded.value }
            .wrapContentSize(Alignment.Center)
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedOption.value,
                color = Color.White
            )
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Dropdown Icon",
                tint = Color.White
            )
        }
    }

    DropdownMenu(
        expanded = isDropDownExpanded.value,
        onDismissRequest = { isDropDownExpanded.value = false },
        modifier = Modifier.background(color = Color.White)
    ) {
        filterOptions.forEach { option ->
            DropdownMenuItem(
                text = { Text(text = option, color = Color.Black) },
                onClick = {
                    selectedOption.value = option
                    isDropDownExpanded.value = false
                }
            )
        }
    }
}
