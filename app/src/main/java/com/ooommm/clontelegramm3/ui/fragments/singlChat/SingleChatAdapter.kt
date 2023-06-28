package com.ooommm.clontelegramm3.ui.fragments.singlChat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ooommm.clontelegramm3.R
import com.ooommm.clontelegramm3.dataBase.CURRENT_UID
import com.ooommm.clontelegramm3.models.CommonModel
import com.ooommm.clontelegramm3.utilits.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    private var listMessageCache = mutableListOf<CommonModel>()

    /////////////////////////////////////////////////////////////////////////////////////////////
    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        //Text
        //User
        val blockUserMessage: ConstraintLayout = view.findViewById(R.id.bloc_user_message)
        val chatUserMessage: TextView = view.findViewById(R.id.chat_user_message)
        val chatUserMessageTime: TextView = view.findViewById(R.id.chat_user_time)

        //Received
        val blocReceivedMessage: ConstraintLayout = view.findViewById(R.id.bloc_receiver_message)
        val chatReceivedMessage: TextView = view.findViewById(R.id.chat_receiver_message)
        val chatReceivedMessageTime: TextView = view.findViewById(R.id.chat_receiver_time)

        //------------------------------------------------------------------------------------------
        //Image
        //User
        val blockUserImage: ConstraintLayout = view.findViewById(R.id.bloc_user_Image)
        val chatUserImage: ImageView = view.findViewById(R.id.chat_user_image)
        val chatUserImageTime: TextView = view.findViewById(R.id.chat_user_image_time)

        //Received
        val blockReceivedImage: ConstraintLayout = view.findViewById(R.id.bloc_received_Image)
        val chatReceivedImage: ImageView = view.findViewById(R.id.chat_user_image)
        val chatReceivedImageTime: TextView = view.findViewById(R.id.chat_receiver_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mesage_item, parent, false)
        return SingleChatHolder(view)
    }

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        when (listMessageCache[position].type) {
            TYPE_MESSAGE_TEXT -> drawMessageText(holder, position)
            TYPE_MESSAGE_IMAGE -> drawMessageImage(holder, position)
        }
    }

    private fun drawMessageImage(holder: SingleChatHolder, position: Int) {
        // отключаем видимость холдер текста
        holder.blockUserMessage.isVisible = false
        holder.blocReceivedMessage.isVisible = false

        if (listMessageCache.get(position).from == CURRENT_UID) {
            // отключаем блок приходящеко изображения и включаем блок исходяшего изображения
            holder.blockUserImage.isVisible = true
            holder.blockReceivedImage.isVisible = false
            // заполняем view  в холдере
            holder.chatUserImage.downloadAndSetImage(listMessageCache.get(position).fileUrl)
            holder.chatUserImageTime.text =
                listMessageCache.get(position).timeStamp.toString().asTime()
        } else {
            //отображаем приходящее сообщение
            holder.blockUserImage.isVisible = false
            holder.blocReceivedMessage.isVisible = true
            // заполняем view  в холдере
            holder.chatReceivedImage.downloadAndSetImage(listMessageCache.get(position).fileUrl)
            holder.chatReceivedImageTime.text =
                listMessageCache.get(position).timeStamp.toString().asTime()
        }
    }

    private fun drawMessageText(holder: SingleChatHolder, position: Int) {
        // отключаем видимость холдера изображения
        holder.blockUserImage.isVisible = false
        holder.blockReceivedImage.isVisible = false

        if (listMessageCache.get(position).from == CURRENT_UID) {
            //отобразить исходящее  и скрыть входящее
            holder.blockUserMessage.isVisible = true
            holder.blocReceivedMessage.isVisible = false
            // заполняем view  в холдере
            holder.chatUserMessage.text = listMessageCache.get(position).text
            holder.chatUserMessageTime.text =
                listMessageCache.get(position).timeStamp.toString().asTime()
        } else {
            //отобразить входящее и скрыть исходящее
            holder.blockUserMessage.isVisible = false
            holder.blocReceivedMessage.isVisible = true
            // заполняем view  в холдере
            holder.chatReceivedMessage.text = listMessageCache.get(position).text
            holder.chatReceivedMessageTime.text =
                listMessageCache.get(position).timeStamp.toString().asTime()

        }
    }

    override fun getItemCount() = listMessageCache.size

    fun addItemToBottom(item: CommonModel, onSuccess: () -> Unit) {
        if (!listMessageCache.contains(item)) {
            listMessageCache.add(item)
            notifyItemInserted(listMessageCache.size)
        }
        onSuccess()
    }

    fun addItemToTop(item: CommonModel, onSuccess: () -> Unit) {
        if (!listMessageCache.contains(item)) {
            listMessageCache.add(item)
            listMessageCache.sortBy { it.timeStamp.toString() }
            notifyItemInserted(0)
        }
        onSuccess()
    }

}




