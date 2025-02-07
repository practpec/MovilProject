package com.example.state.tasks.data.datasource

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import com.example.state.tasks.domain.AudioPlayer
import java.io.File

class AndroidAudioPlayer(
    private val context: Context
): AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: File) {
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            start()
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}