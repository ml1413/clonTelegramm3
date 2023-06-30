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
import com.ooommm.clontelegramm3.utilits.asTime
import com.ooommm.clontelegramm3.utilits.downloadAndSetImage

class HolderImageMessage(view: View) : RecyclerView.ViewHolder(view), MessageHolder {
    //Image
    //User
    private val blockUserImage: ConstraintLayout = view.findViewById(R.id.bloc_user_Image)
    private val chatUserImage: ImageView = view.findViewById(R.id.chat_user_image)
    private val chatUserImageTime: TextView = view.findViewById(R.id.chat_user_image_time)

    //Received
    private val blockReceivedImage: ConstraintLayout = view.findViewById(R.id.bloc_received_Image)
    private val chatReceivedImage: ImageView = view.findViewById(R.id.chat_received_image)
    private val chatReceivedImageTime: TextView = view.findViewById(R.id.chat_user_image_time)


    override fun drawHolder(view: MessageView) {
        if (view.from == CURRENT_UID) {
            // отключаем блок приходящеко изображения и включаем блок исходяшего изображения
            blockUserImage.isVisible = true
            blockReceivedImage.isVisible = false
            // заполняем view  в холдере
            chatUserImage.downloadAndSetImage(view.fileUrl)
            chatUserImageTime.text =
                view.timeStamp.asTime()
        } else {
            //отображаем приходящее сообщение
            blockUserImage.isVisible = false
            blockReceivedImage.isVisible = true
            // заполняем view  в холдере
            chatReceivedImage.downloadAndSetImage(view.fileUrl)
            chatReceivedImageTime.text =
                view.timeStamp.asTime()
        }
    }

    override fun onAttach(view: MessageView) {
    }

    override fun onDetach() {
    }

}