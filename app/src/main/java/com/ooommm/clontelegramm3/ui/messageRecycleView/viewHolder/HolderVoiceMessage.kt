package com.ooommm.clontelegramm3.ui.messageRecycleView.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.CURRENT_UID
import com.ooommm.clontelegramm3.ui.messageRecycleView.views.MessageView
import com.ooommm.clontelegramm3.utilits.AppVoicePlayer
import com.ooommm.clontelegramm3.utilits.asTime

class HolderVoiceMessage(view: View) : RecyclerView.ViewHolder(view), MessageHolder {

    private val appVoicePlayer = AppVoicePlayer()

    //Image
    //User
    val blockUserVoice: ConstraintLayout = view.findViewById(R.id.bloc_user_voice_message)
    val chatUserVoiceTime: TextView = view.findViewById(R.id.chat_user_voice_time)

    val chatUserBtnVoicePlay: ImageView = view.findViewById(R.id.chat_user_btn_play)
    val chatUserBtnVoiceStop: ImageView = view.findViewById(R.id.chat_user_btn_stop)

    //Received
    val blockReceivedVoice: ConstraintLayout = view.findViewById(R.id.bloc_receiver_voice_message)
    val chatReceivedVoiceTime: TextView = view.findViewById(R.id.chat_receiver_voice_time)

    val chatReceivedBtnVoicePlay: ImageView = view.findViewById(R.id.chat_received_btn_play)
    val chatReceivedBtnVoiceStop: ImageView = view.findViewById(R.id.chat_received_btn_stop)


    override fun drawHolder(view: MessageView) {
        if (view.from == CURRENT_UID) {
            // отключаем блок приходящеко сообщения и включаем блок исходяшего сообщения
            blockUserVoice.isVisible = true
            blockReceivedVoice.isVisible = false
            // заполняем view  в холдере
            chatUserVoiceTime.text =
                view.timeStamp.asTime()
        } else {
            //отображаем приходящее сообщение
            blockUserVoice.isVisible = false
            blockReceivedVoice.isVisible = true
            // заполняем view  в холдере
            chatReceivedVoiceTime.text = view.timeStamp.asTime()
        }
    }

    // нажатие на кнопку проигравания voice  сообщения
    override fun onAttach(view: MessageView) {
        appVoicePlayer.init()

        if (view.from == CURRENT_UID) {
            chatUserBtnVoicePlay.setOnClickListener {
                chatUserBtnVoicePlay.isVisible = false
                chatUserBtnVoiceStop.isVisible = true
                play(view) {
                    chatUserBtnVoicePlay.isVisible = true
                    chatUserBtnVoiceStop.isVisible = false
                }
            }
        } else {
            chatReceivedBtnVoicePlay.setOnClickListener {
                chatReceivedBtnVoicePlay.isVisible = false
                chatReceivedBtnVoiceStop.isVisible = true
                play(view) {
                    chatReceivedBtnVoicePlay.isVisible = true
                    chatReceivedBtnVoiceStop.isVisible = false
                }
            }
        }
    }

    private fun play(view: MessageView, function: () -> Unit) {
        appVoicePlayer.play(messageKey = view.id, fileUrl = view.fileUrl) {
            function()
        }
    }

    override fun onDettach() {
        chatUserBtnVoicePlay.setOnClickListener(null)
        chatReceivedBtnVoicePlay.setOnClickListener(null)
        appVoicePlayer.release()
    }
}

