package com.ooommm.clontelegramm3.ui.fragments.messageRecycleView.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R

class HolderImageMessage(view: View) : RecyclerView.ViewHolder(view) {
    //Image
    //User
    val blockUserImage: ConstraintLayout = view.findViewById(R.id.bloc_user_Image)
    val chatUserImage: ImageView = view.findViewById(R.id.chat_user_image)
    val chatUserImageTime: TextView = view.findViewById(R.id.chat_user_image_time)

    //Received
    val blockReceivedImage: ConstraintLayout = view.findViewById(R.id.bloc_received_Image)
    val chatReceivedImage: ImageView = view.findViewById(R.id.chat_user_image)
    val chatReceivedImageTime: TextView = view.findViewById(R.id.chat_user_image_time)
}