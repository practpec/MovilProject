package com.example.state.tasks.domain

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
}