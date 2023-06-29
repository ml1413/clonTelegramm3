package com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.CURRENT_UID
import com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.views.MessageView
import com.ooommm.clontelegramm3.utilits.asTime

class HolderVoiceMessage(view: View) : RecyclerView.ViewHolder(view) {
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


    fun drawMessageVoice(holder: HolderVoiceMessage, view: MessageView) {
        if (view.from == CURRENT_UID) {
            // отключаем блок приходящеко сообщения и включаем блок исходяшего сообщения
            holder.blockUserVoice.isVisible = true
            holder.blockReceivedVoice.isVisible = false
            // заполняем view  в холдере
            holder.chatUserVoiceTime.text =
                view.timeStamp.asTime()
        } else {
            //отображаем приходящее сообщение
            holder.blockUserVoice.isVisible = false
            holder.blockReceivedVoice.isVisible = true
            // заполняем view  в холдере
            holder.chatReceivedVoiceTime.text = view.timeStamp.asTime()
        }
    }
}

