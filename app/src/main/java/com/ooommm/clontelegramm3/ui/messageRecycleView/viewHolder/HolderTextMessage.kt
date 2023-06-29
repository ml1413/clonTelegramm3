package com.ooommm.clontelegramm3.ui.messageRecycleView.viewHolder

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.CURRENT_UID
import com.ooommm.clontelegramm3.ui.messageRecycleView.views.MessageView
import com.ooommm.clontelegramm3.utilits.asTime

class HolderTextMessage(view: View) : RecyclerView.ViewHolder(view), MessageHolder {
    //Text
    //User
    val blockUserMessage: ConstraintLayout = view.findViewById(R.id.bloc_user_message)
    val chatUserMessage: TextView = view.findViewById(R.id.chat_user_message)
    val chatUserMessageTime: TextView = view.findViewById(R.id.chat_user_time)

    //Received
    val blocReceivedMessage: ConstraintLayout = view.findViewById(R.id.bloc_receiver_message)
    val chatReceivedMessage: TextView = view.findViewById(R.id.chat_receiver_message)
    val chatReceivedMessageTime: TextView = view.findViewById(R.id.chat_receiver_time)


    override fun drawHolder(view: MessageView) {
        if (view.from == CURRENT_UID) {
            //отобразить исходящее  и скрыть входящее
            blockUserMessage.isVisible = true
            blocReceivedMessage.isVisible = false
            // заполняем view  в холдере
            chatUserMessage.text = view.text
            chatUserMessageTime.text =
                view.timeStamp.asTime()
        } else {
            //отобразить входящее и скрыть исходящее
            blockUserMessage.isVisible = false
            blocReceivedMessage.isVisible = true
            // заполняем view  в холдере
            chatReceivedMessage.text = view.text
            chatReceivedMessageTime.text =
                view.timeStamp.asTime()

        }
    }
    override fun onAttach(view: MessageView) {
    }

    override fun onDettach() {
    }

}