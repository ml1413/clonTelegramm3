package com.ooommm.clontelegramm3.utilits

import android.media.MediaPlayer
import com.ooommm.clontelegramm3.dataBase.getFileFromStorage
import java.io.File

class AppVoicePlayer {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var file: File

    fun play(messageKey: String, fileUrl: String, function: () -> Unit) {
        file = File(APP_ACTIVITY.filesDir, messageKey)
        // если файл существует
        if (file.exists() && file.isFile && file.length() > 0) {
            startPlay {
                function()
            }
        } else {
            file.createNewFile()
            getFileFromStorage(file, fileUrl) {
                startPlay {
                    function()
                }
            }
        }
    }


    private fun startPlay(function: () -> Unit) {
        try {
            mediaPlayer.setDataSource(file.absolutePath)// путь к проигрываемому файлу
            mediaPlayer.prepare()// подготовка плеера
            mediaPlayer.start()
            // отслеживание окончания проигрывания файла
            mediaPlayer.setOnCompletionListener {
                stop {
                    function()
                }
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }

    private fun stop(function: () -> Unit) {
        try {
            mediaPlayer.stop()
            mediaPlayer.reset()
            function()
        } catch (e: Exception) {
            showToast(e.message.toString())
            function()
        }
    }

    fun release() {
        mediaPlayer.release()
    }

    fun init() {
        mediaPlayer = MediaPlayer()
    }
}