package com.example.state.tasks.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.state.ui.components.FilterDropdownMenu
import com.example.state.ui.components.FontBackground
import com.example.state.ui.components.TaskCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen( token: String,
                 idUser: String,
                 onLogout: () -> Unit,
                 onCreateTask: () -> Unit,
                 onEditTask: (String) -> Unit) {
    FontBackground(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TopAppBar(
                title = { Text("Tasks") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
                actions = {
                    FilterDropdownMenu()
                    FloatingActionButton(
                        onClick = { onCreateTask() },
                        containerColor = Color.Transparent,
                        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White
                        )
                    }
                    //icono de cerrar sesion
                    IconButton(onClick = { onLogout()  }) {
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

                val items = listOf("Item 1", "Item 2", "Item 3")

                items.forEach { item ->
                    val isChecked = remember { mutableStateOf(false) }
                    TaskCard(
                        item = item,
                        onCheckedChange = { isChecked.value = it },
                        isChecked = isChecked.value
                    )
                }
            }
        }
    }
}

