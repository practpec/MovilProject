package com.example.state.tasks.domain

import com.example.state.tasks.data.model.CreateTaskRequest
import com.example.state.tasks.data.model.TaskDTO
import com.example.state.tasks.data.repository.TasksRepository

class CreateTaskUseCase {
    private val repository = TasksRepository()

    suspend operator fun invoke(task: CreateTaskRequest):Result<TaskDTO>{
        val result = repository.createTask(task)
        return result
    }
}

