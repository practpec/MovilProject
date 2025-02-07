package com.example.state.tasks.data.model

class TaskDTO (
    val id_task: Int = 0,
    val task: String,
    val description: String,
    val date_limit: String,
    val status: Int = 0,
    val id_user: Int,
    val created_at: String = ""
)