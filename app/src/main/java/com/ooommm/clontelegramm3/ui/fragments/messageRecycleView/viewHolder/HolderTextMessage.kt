package com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.viewHolder

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R

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
}