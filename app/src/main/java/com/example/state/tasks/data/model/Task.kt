package com.example.state.tasks.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDTO(
    @SerialName("id_task")
    val id_task: Int,
    val task: String,
    val description: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("date_limit")
    val date_limit: String,
    val status: Int,
    @SerialName("id_user")
    val id_user: Int,
    val updatedAt: String?
)
