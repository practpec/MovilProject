package com.example.state.tasks.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.state.tasks.data.datasource.AndroidAudioPlayer
import com.example.state.tasks.data.datasource.AndroidAudioRecorder
import com.example.state.tasks.data.model.CreateTaskRequest
import com.example.state.tasks.data.model.TaskDTO
import com.example.state.tasks.data.repository.AudioRepository
import com.example.state.tasks.data.repository.TasksRepository
import com.example.state.tasks.domain.CompleteTaskUseCase
import com.example.state.tasks.domain.CreateAudioUseCase
import com.example.state.tasks.domain.CreateTaskUseCase
import com.example.state.tasks.domain.DeleteAudioFileUseCase
import com.example.state.tasks.domain.DeleteTaskUseCase
import com.example.state.tasks.domain.GetAudioFileUseCase
import com.example.state.tasks.domain.GetTasksUseCase
import kotlinx.coroutines.launch
import java.io.File


class TasksViewModel(private val context: Context) : ViewModel() {
    private val _tasks = MutableLiveData<List<TaskDTO>>(emptyList())
    private val tasksRepository = TasksRepository()
   private val audioRepository = AudioRepository(context)
    private val getTasksUseCase = GetTasksUseCase(tasksRepository)
    private val createTaskUseCase = CreateTaskUseCase()
    private val deleteTaskUseCase = DeleteTaskUseCase(tasksRepository)
    private val completeTaskUseCase = CompleteTaskUseCase(tasksRepository)
    private val createAudioUseCase = CreateAudioUseCase(audioRepository)
    private val deleteAudioFileUseCase = DeleteAudioFileUseCase(audioRepository)
    private val getAudioFileUseCase = GetAudioFileUseCase(audioRepository)



    private val recorder by lazy {
        AndroidAudioRecorder(context)
    }

    private val player by lazy {
        AndroidAudioPlayer(context)
    }

    val tasks: LiveData<List<TaskDTO>> = _tasks

    private val _error = MutableLiveData("")
    val error: LiveData<String> = _error

    private val _selectedTaskId = MutableLiveData<Int?>()
    val selectedTaskId: LiveData<Int?> = _selectedTaskId

    private val _taskDetail = MutableLiveData<TaskDTO?>()
    val taskDetail: LiveData<TaskDTO?> = _taskDetail

    private var audioFile: File? = null



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
                deleteTaskUseCase(taskId)
                deleteAudio(taskId)
                _tasks.value = _tasks.value?.filter { it.id_task != taskId.toInt() }
                _selectedTaskId.value = null
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
                    saveAudio(data.id_task.toString())
                    deleteRecording()
                }.onFailure { exception ->
                    _error.value = exception.message ?: "Error al crear la tarea."
                }
            } catch (e: Exception) {
                _error.value = "Error de red al crear la tarea: ${e.message}"
            }
        }
    }

    fun selectTaskToDelete(taskId: Int) {
        _selectedTaskId.value = taskId
    }

    fun clearSelectedTask() {
        _selectedTaskId.value = null
    }

    fun selectTaskForDetail(task: TaskDTO) {
        _taskDetail.value = task
    }

    fun clearTaskDetail() {
        _taskDetail.value = null
    }
    //Fun iniciar grabacion
    fun startRecording() {
        audioFile = File(context.cacheDir, "audio.mp3")
        audioFile?.let {
            recorder.start(it)
        }
    }


    //Fun eliminar
    fun deleteRecording() {
        val audioFile = File(context.cacheDir, "audio.mp3")
        //context.cacheDir.
        if (audioFile.exists()) {
            if (audioFile.delete()) {
                println("Se a eliminado con éxito.")
            } else {
                println("No se pudo eliminar.")
            }
        } else {
            println("El archivo de grabación no existe.")
        }
    }

    // Fun detener grabacion
    fun stopRecording() {
        recorder.stop()
    }

    // Fun reproducir audio
    fun playAudio() {
        audioFile?.let {
            player.playFile(it)
        }
    }


    fun saveAudio(id_task: String) {

        audioFile?.let {
            if (it.exists() && it.length() > 0) {
               createAudioUseCase.execute(id_task, it)
            } else {
                _error.value = "El audio está vacío o no se grabó correctamente."
                println("Error: El aaudio está vacío o no se grabó correctamente.")
            }
        } ?: run {
            _error.value = "No se ha grabado ningún audio."
            println("Error: No se ha grabado ningún audio.")
        }
    }

    fun deleteAudio(taskId: String) {
        viewModelScope.launch {
            try {
                deleteAudioFileUseCase.execute(taskId)
            } catch (e: Exception) {
                _error.value = "Error inesperado: ${e.message}"
            }
        }
    }

    fun getLocalAudioFile(id_task: String, onResult: (File?) -> Unit) {
        viewModelScope.launch {
            try {
                val audioFile = getAudioFileUseCase.execute(id_task)
                if (audioFile?.exists() == true) {
                    onResult(audioFile)
                } else {
                    onResult(null)
                    _error.value = "El archivo de audio no se encontró."
                }
            } catch (e: Exception) {
                _error.value = "Error inesperado: ${e.message}"
                onResult(null)
            }
        }
    }



    fun playAudioFile(id_task: String) {
        getLocalAudioFile(id_task) { audioFile ->
            if (audioFile != null) {
                player.playFile(audioFile)  // Reproducir si el archivo existe
            } else {
                _error.value = "No se encontró un archivo de audio para esta tarea."
            }
        }
    }
}
