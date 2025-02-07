package com.example.state.tasks.domain

import com.example.state.tasks.data.repository.AudioRepository

class DeleteAudioFileUseCase(private val audioRepository: AudioRepository) {

    suspend fun execute(taskId: String) {
        try {
            audioRepository.deleteAudioFile(taskId)
        } catch (e: Exception) {
            throw Exception("Error al eliminar el archivo de audio: ${e.message}")
        }
    }
}
