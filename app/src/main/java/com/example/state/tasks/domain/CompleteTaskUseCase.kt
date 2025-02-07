package com.example.state.tasks.domain
import com.example.state.tasks.data.model.TaskDTO
import com.example.state.tasks.data.repository.TasksRepository

class CompleteTaskUseCase(private val repository: TasksRepository) {

    suspend operator fun invoke(taskId: String): TaskDTO {
        return repository.completeTask(taskId)
    }
}
