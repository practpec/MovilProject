package com.example.state.tasks.domain

import com.example.state.tasks.data.model.TaskDTO
import com.example.state.tasks.data.repository.TasksRepository

class GetTasksUseCase(private val repository: TasksRepository) {

    suspend operator fun invoke(idUser: String): Result<List<TaskDTO>> {
        return repository.getTasks(idUser)
    }
}
