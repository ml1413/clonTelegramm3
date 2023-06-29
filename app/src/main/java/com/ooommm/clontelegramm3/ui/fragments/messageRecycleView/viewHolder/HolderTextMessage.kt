package com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.viewHolder

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.CURRENT_UID
import com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.views.MessageView
import com.ooommm.clontelegramm3.utilits.asTime

class HolderTextMessage(view: View) : RecyclerView.ViewHolder(view) {
    //Text
    //User
    val blockUserMessage: ConstraintLayout = view.findViewById(R.id.bloc_user_message)
    val chatUserMessage: TextView = view.findViewById(R.id.chat_user_message)
    val chatUserMessageTime: TextView = view.findViewById(R.id.chat_user_time)

    //Received
    val blocReceivedMessage: ConstraintLayout = view.findViewById(R.id.bloc_receiver_message)
    val chatReceivedMessage: TextView = view.findViewById(R.id.chat_receiver_message)
    val chatReceivedMessageTime: TextView = view.findViewById(R.id.chat_receiver_time)

    fun drawMessageText(holder: HolderTextMessage, view: MessageView) {

        if (view.from == CURRENT_UID) {
            //отобразить исходящее  и скрыть входящее
            holder.blockUserMessage.isVisible = true
            holder.blocReceivedMessage.isVisible = false
            // заполняем view  в холдере
            holder.chatUserMessage.text = view.text
            holder.chatUserMessageTime.text =
                view.timeStamp.asTime()
        } else {
            //отобразить входящее и скрыть исходящее
            holder.blockUserMessage.isVisible = false
            holder.blocReceivedMessage.isVisible = true
            // заполняем view  в холдере
            holder.chatReceivedMessage.text = view.text
            holder.chatReceivedMessageTime.text =
                view.timeStamp.asTime()

        }
    }

}