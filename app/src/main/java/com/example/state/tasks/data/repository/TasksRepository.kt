package com.example.state.tasks.data.repository

import com.example.state.core.network.RetrofitHelper
import com.example.state.register.data.model.CreateUserRequest
import com.example.state.register.data.model.UserDTO
import com.example.state.tasks.data.datasource.TasksService
import com.example.state.tasks.data.model.CreateTaskRequest
import com.example.state.tasks.data.model.TaskDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TasksRepository {

    private val tasksService = RetrofitHelper.tasksService

    suspend fun getTasks(idUser: String): Result<List<TaskDTO>> {
        return try {
            val response = tasksService.getTasks(idUser)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error al obtener las tareas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createTask(request: CreateTaskRequest): Result<TaskDTO> {
        return try {
            val response = tasksService.createTask(request)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteTask(taskId: String) {
        withContext(Dispatchers.IO) {
            tasksService.deleteTask(taskId)
        }
    }


    suspend fun completeTask(taskId: String): TaskDTO {
        return withContext(Dispatchers.IO) {
            tasksService.toggleTaskCompletion(taskId)
        }
    }
}
