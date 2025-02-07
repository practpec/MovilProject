package com.example.state.tasks.domain
import com.example.state.tasks.data.repository.TasksRepository

class DeleteTaskUseCase(private val repository: TasksRepository) {

    suspend operator fun invoke(taskId: String) {
        return repository.deleteTask(taskId)
    }
}
