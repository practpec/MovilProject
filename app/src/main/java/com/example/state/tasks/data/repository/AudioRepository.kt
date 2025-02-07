package com.example.state.tasks.data.repository

import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AudioRepository(private val context: Context) {

    fun saveAudioFile(id_task: String, audioFile: File) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Audio.Media.DISPLAY_NAME, "${id_task}_audio.mp3")
                put(MediaStore.Audio.Media.MIME_TYPE, "audio/mp3")
                put(MediaStore.Audio.Media.RELATIVE_PATH, "Music/Task")
            }

            val resolver: ContentResolver = context.contentResolver
            val audioUri: Uri? = resolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues)

            audioUri?.let { uri ->
                try {
                    val outputStream = resolver.openOutputStream(uri)
                    audioFile.inputStream().copyTo(outputStream!!)
                    outputStream.close()
                } catch (e: IOException) {
                    println("Error al guardar el archivo: ${e.message}")
                }
            }
        } else {
            val musicDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "Task")
            if (!musicDirectory.exists()) {
                musicDirectory.mkdirs()
            }

            val outputFile = File(musicDirectory, "${id_task}_audio.mp3")
            try {
                val fileOutputStream = FileOutputStream(outputFile)
                audioFile.inputStream().copyTo(fileOutputStream)
                fileOutputStream.close()
            } catch (e: IOException) {
                println("Error al guardar el archivo: ${e.message}")
            }
        }
    }

    fun deleteAudioFile(taskId: String) {
        val musicDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "Task")
        val audioFile = File(musicDirectory, "${taskId}_audio.mp3")

        if (audioFile.exists()) {
            if (audioFile.delete()) {
                println("Archivo de audio $taskId eliminado.")
            } else {
                println("No se pudo eliminar el archivo de audio.")
            }
        } else {
            println("El archivo de audio no existe.")
        }
    }

    fun getAudioFileForTask(id_task: String): File? {
        val audioDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "Task")
        val audioFile = File(audioDirectory, "${id_task}_audio.mp3")
        return if (audioFile.exists()) audioFile else null
    }
}
