package com.example.state.tasks.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.state.core.network.RetrofitHelper
import com.example.state.tasks.data.model.CreateTaskRequest
import com.example.state.tasks.data.model.TaskDTO
import com.example.state.tasks.data.repository.TasksRepository
import com.example.state.tasks.domain.CompleteTaskUseCase
import com.example.state.tasks.domain.CreateTaskUseCase
import com.example.state.tasks.domain.DeleteTaskUseCase
import com.example.state.tasks.domain.GetTasksUseCase
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {
    private val _tasks = MutableLiveData<List<TaskDTO>>(emptyList())
    private val tasksRepository = TasksRepository()
    private val getTasksUseCase = GetTasksUseCase(tasksRepository)
    private val createTaskUseCase = CreateTaskUseCase()
    private val deleteTaskUseCase = DeleteTaskUseCase(tasksRepository)
    private val completeTaskUseCase = CompleteTaskUseCase(tasksRepository)

    val tasks: LiveData<List<TaskDTO>> = _tasks

    private val _error = MutableLiveData("")
    val error: LiveData<String> = _error

    fun loadTasks(idUser: String) {
        viewModelScope.launch {
            try {

                val result = getTasksUseCase(idUser)
                result.onSuccess {
                    _tasks.value = it
                }.onFailure {
                    _error.value = "Error cargando tareas: ${it.message}"
                }
            } catch (e: Exception) {
                _error.value = "Error inesperado: ${e.message}"
            }
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            try {
                val result = deleteTaskUseCase(taskId)
                _tasks.value = _tasks.value?.filter { it.id_task != taskId.toInt() }
            } catch (e: Exception) {
                _error.value = "Error inesperado: ${e.message}"
            }
        }
    }


    fun toggleTaskCompletion(taskId: String) {
        viewModelScope.launch {
            try {
                val updatedTask = completeTaskUseCase(taskId)
                _tasks.value = _tasks.value?.map { task ->
                    if (task.id_task == updatedTask.id_task) updatedTask else task
                }
            } catch (e: Exception) {
                _error.value = "Error al actualizar el estado de la tarea: ${e.message}"
            }
        }
    }

    fun createTask(task: CreateTaskRequest) {
        viewModelScope.launch {
            try {
                val resultCreateTask = createTaskUseCase(task)

                resultCreateTask.onSuccess { data ->
                    _tasks.value = _tasks.value?.let { currentTasks ->
                        currentTasks.toMutableList().apply { add(data) }
                    }
                }.onFailure { exception ->
                    _error.value = exception.message ?: "Error al crear la tarea."
                }
            } catch (e: Exception) {
                _error.value = "Error de red al crear la tarea: ${e.message}"
            }
        }
    }


}
