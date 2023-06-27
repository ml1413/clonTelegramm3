package com.ooommm.clontelegramm3.utilits

import android.media.MediaRecorder
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class AppVoiceRecorder {

    private val mediaRecorder = MediaRecorder()
    private lateinit var file: File
    private lateinit var messageKey: String

    fun startRecord(messageKey: String) {
        try {
            this.messageKey = messageKey
            createFileOnRecord()
            prepareMediaRecorder()
            /*7*/mediaRecorder.start()//запись
        } catch (e: Exception) {
            showToast(e.toString())
        }

    }

    private fun prepareMediaRecorder() {
        /*1*/ mediaRecorder.reset()// сброс на всякий случай
        /*2*/ mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT)//откуда поток данных
        /*3*/ mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)//в каком формате запись
        /*4*/ mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)//енкодер
        //проверка версии + путь к записанному файлу
        /*5*/ if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mediaRecorder.setOutputFile(file.absolutePath)
        }

        /*6*/ mediaRecorder.prepare()//подготовка

    }

    private fun createFileOnRecord() {
        file = File(APP_ACTIVITY.filesDir, messageKey)
        file.createNewFile()
    }

    fun stopRecord(onSuccess: (file: File, messageKey: String) -> Unit) {
        try {
            mediaRecorder.stop()//остановка
            onSuccess(file, messageKey)
        } catch (e: Exception) {
            showToast(e.toString())
            file.delete()
        }
    }

    fun releaseRecord() {
        try {
            mediaRecorder.release()//удаляем из памяти
        } catch (e: Exception) {
            showToast(e.toString())
        }
    }
}