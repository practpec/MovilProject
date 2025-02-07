package com.example.state.tasks.domain

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}