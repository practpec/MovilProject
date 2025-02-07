package com.example.state.tasks.domain

import com.example.state.tasks.data.repository.AudioRepository
import java.io.File

class GetAudioFileUseCase(private val audioRepository: AudioRepository) {

    fun execute(id_task: String): File? {
        return audioRepository.getAudioFileForTask(id_task)
    }
}
