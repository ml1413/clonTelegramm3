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
    private val blockUserVoice: ConstraintLayout = view.findViewById(R.id.bloc_user_voice_message)
    private val chatUserVoiceTime: TextView = view.findViewById(R.id.chat_user_voice_time)

    private val chatUserBtnVoicePlay: ImageView = view.findViewById(R.id.chat_user_btn_play)
    private val chatUserBtnVoiceStop: ImageView = view.findViewById(R.id.chat_user_btn_stop)

    //Received
    private val blockReceivedVoice: ConstraintLayout =
        view.findViewById(R.id.bloc_receiver_voice_message)
    private val chatReceivedVoiceTime: TextView = view.findViewById(R.id.chat_receiver_voice_time)

    private val chatReceivedBtnVoicePlay: ImageView = view.findViewById(R.id.chat_received_btn_play)
    private val chatReceivedBtnVoiceStop: ImageView = view.findViewById(R.id.chat_received_btn_stop)


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
                // подключает стушатель на кнопку стоп
                chatUserBtnVoiceStop.setOnClickListener {
                    stop {
                        // удаляем слушатель нажатия
                        chatUserBtnVoiceStop.setOnClickListener(null)

                        chatUserBtnVoicePlay.isVisible = true
                        chatUserBtnVoiceStop.isVisible = false
                    }
                }

                play(view) {
                    chatUserBtnVoicePlay.isVisible = true
                    chatUserBtnVoiceStop.isVisible = false
                }
            }
        } else {
            chatReceivedBtnVoicePlay.setOnClickListener {
                chatReceivedBtnVoicePlay.isVisible = false
                chatReceivedBtnVoiceStop.isVisible = true

                chatReceivedBtnVoiceStop.setOnClickListener {
                    stop {
                        // удаляем слушатель нажатия
                        chatReceivedBtnVoiceStop.setOnClickListener(null)

                        chatReceivedBtnVoicePlay.isVisible = true
                        chatReceivedBtnVoiceStop.isVisible = false
                    }
                }

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

    fun stop(function: () -> Unit) {
        appVoicePlayer.stop {
            function()
        }
    }

    override fun onDetach() {
        chatUserBtnVoicePlay.setOnClickListener(null)
        chatReceivedBtnVoicePlay.setOnClickListener(null)
        appVoicePlayer.release()
    }
}

