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
import com.ooommm.clontelegramm3.utilits.downloadAndSetImage

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


    fun drawMessageImage(holder: HolderImageMessage, view: MessageView) {

        if (view.from == CURRENT_UID) {
            // отключаем блок приходящеко изображения и включаем блок исходяшего изображения
            holder.blockUserImage.isVisible = true
            holder.blockReceivedImage.isVisible = false
            // заполняем view  в холдере
            holder.chatUserImage.downloadAndSetImage(view.fileUrl)
            holder.chatUserImageTime.text =
                view.timeStamp.asTime()
        } else {
            //отображаем приходящее сообщение
            holder.blockUserImage.isVisible = false
            holder.blockReceivedImage.isVisible = true
            // заполняем view  в холдере
            holder.chatReceivedImage.downloadAndSetImage(view.fileUrl)
            holder.chatReceivedImageTime.text =
                view.timeStamp.asTime()
        }
    }

}