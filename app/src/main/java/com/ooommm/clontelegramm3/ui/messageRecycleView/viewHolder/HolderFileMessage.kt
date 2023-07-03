package com.ooommm.clontelegramm3.ui.messageRecycleView.viewHolder

import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.view.menu.ExpandedMenuView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.CURRENT_UID
import com.ooommm.clontelegramm3.dataBase.getFileFromStorage
import com.ooommm.clontelegramm3.ui.messageRecycleView.views.MessageView
import com.ooommm.clontelegramm3.utilits.WRITE_FILES
import com.ooommm.clontelegramm3.utilits.asTime
import com.ooommm.clontelegramm3.utilits.checkPermission
import com.ooommm.clontelegramm3.utilits.showToast
import java.io.File

class HolderFileMessage(view: View) : RecyclerView.ViewHolder(view), MessageHolder {
    //file
    //User
    private val blockUserFile: ConstraintLayout = view.findViewById(R.id.bloc_user_file_message)
    private val chatUserBthDownload: ImageView = view.findViewById(R.id.chat_user_btn_download)
    private val chatUserFileTime: TextView = view.findViewById(R.id.chat_user_file_time)
    private val chatUserFileMessage: TextView = view.findViewById(R.id.chat_user_file_message)
    private val chatUserProgressBar: ProgressBar = view.findViewById(R.id.chat_user_progressbar)

    //Received
    private val blockReceivedFile: ConstraintLayout =
        view.findViewById(R.id.bloc_receiver_file_message)
    private val chatReceivedBthDownload: ImageView =
        view.findViewById(R.id.chat_received_btn_download)
    private val chatReceivedFileTime: TextView = view.findViewById(R.id.chat_receiver_file_time)
    private val chatReceivedProgressBar: ProgressBar =
        view.findViewById(R.id.chat_received_progressbar)
    private val chatReceivedMessage: TextView = view.findViewById(R.id.chat_receiver_file_message)


    override fun drawHolder(view: MessageView) {
        if (view.from == CURRENT_UID) {
            // отключаем блок приходящеко сообщения и включаем блок исходяшего сообщения
            blockUserFile.isVisible = true
            blockReceivedFile.isVisible = false
            // заполняем view  в холдере
            chatUserFileTime.text = view.timeStamp.asTime()
            chatUserFileMessage.text = view.text

        } else {
            //отображаем приходящее сообщение
            blockUserFile.isVisible = false
            blockReceivedFile.isVisible = true
            // заполняем view  в холдере
            chatReceivedFileTime.text = view.timeStamp.asTime()
            chatReceivedMessage.text = view.text
        }
    }


    override fun onAttach(view: MessageView) {
        if (view.from == CURRENT_UID) chatUserBthDownload.setOnClickListener { clickBtnFile(view) }
        else chatReceivedBthDownload.setOnClickListener { clickBtnFile(view) }
    }

    private fun clickBtnFile(view: MessageView) {
        if (view.from == CURRENT_UID) {
            chatUserBthDownload.isInvisible = true
            chatUserProgressBar.isInvisible = false
        } else {
            chatReceivedBthDownload.isInvisible = true
            chatReceivedProgressBar.isInvisible = false
        }

        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            view.text
        )
        try {
            if (checkPermission(WRITE_FILES)) {
                file.createNewFile()
                getFileFromStorage(file, view.fileUrl) {
                    if (view.from == CURRENT_UID) {
                        chatUserBthDownload.isInvisible = false
                        chatUserProgressBar.isInvisible = true
                        chatUserBthDownload.setImageResource(R.drawable.ic_baseline_insert_drive_file_24)
                    } else {
                        chatReceivedBthDownload.isInvisible = false
                        chatReceivedProgressBar.isInvisible = true
                        chatReceivedBthDownload.setImageResource(R.drawable.ic_baseline_insert_drive_file_24)
                    }
                }
            }
        } catch (e: Exception) {
            showToast(e.message.toString())
            Log.d("TAG1", "clickBtnFile: $e")
        }
    }


    override fun onDetach() {
        chatUserBthDownload.setOnClickListener(null)
        chatReceivedBthDownload.setOnClickListener(null)
    }
}

