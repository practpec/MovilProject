package com.example.state.tasks.domain

import com.example.state.tasks.data.repository.AudioRepository
import java.io.File

class CreateAudioUseCase(private val audioRepository: AudioRepository) {

    fun execute(id_task: String, audioFile: File) {
        audioRepository.saveAudioFile(id_task, audioFile)
    }
}
